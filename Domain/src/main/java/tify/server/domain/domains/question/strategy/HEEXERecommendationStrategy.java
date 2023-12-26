package tify.server.domain.domains.question.strategy;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.question.adaptor.FavorAnswerAdaptor;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.dto.condition.FavorRecommendationDTO;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HEEXERecommendationStrategy implements ProductRecommendationStrategy {

    private final ProductAdaptor productAdaptor;
    private final FavorAnswerAdaptor favorAnswerAdaptor;

    private static final String CATEGORY_NAME = "HEEXE";

    @Override
    public List<Product> recommendation(
            Long userId, String categoryName, List<FavorRecommendationDTO> dto) {

        List<FavorRecommendationDTO> recommendationDTO = getRecommendDTO(userId);

        List<String> splitAnswer =
                Arrays.stream(recommendationDTO.get(0).getAnswer().split(", ")).toList();

        if (splitAnswer.contains("그 외")) {
            return etcStep(splitAnswer);
        } else {
            return specificStep(splitAnswer);
        }
    }

    private List<FavorRecommendationDTO> getRecommendDTO(Long userId) {
        List<FavorAnswer> favorAnswers = new ArrayList<>();
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 2L));
        return favorAnswers.stream().map(FavorRecommendationDTO::from).toList();
    }

    private List<Product> etcStep(List<String> splitAnswer) { // 그 외 라는 답변 존재할 때
        if (splitAnswer.size() > 1) { // ex) 그 외, 헬스
            String answer = splitAnswer.stream().filter(s -> !s.contains("그 외")).toString();
            return productAdaptor.queryAllByCategoryNameAndCharacter(CATEGORY_NAME, answer);
        } else { // ex) 그 외
            return productAdaptor.queryAllByCategoryName(CATEGORY_NAME);
        }
    }

    private List<Product> specificStep(List<String> splitAnswer) { // 그 외 라는 답변 존재하지 않을 때
        List<Product> result = new ArrayList<>();
        if (splitAnswer.size() > 1) { // ex) 헬스, 요가&필라테스
            result.addAll(
                    productAdaptor.queryAllByCategoryNameAndCharacter(
                            CATEGORY_NAME, splitAnswer.get(0)));
            result.addAll(
                    productAdaptor.queryAllByCategoryNameAndCharacter(
                            CATEGORY_NAME, splitAnswer.get(1)));
        } else { // ex) 헬스
            result.addAll(
                    productAdaptor.queryAllByCategoryNameAndCharacter(
                            CATEGORY_NAME, splitAnswer.get(0)));
        }
        return result;
    }
}
