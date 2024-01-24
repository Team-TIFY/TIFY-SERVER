package tify.server.domain.domains.question.strategy;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tify.server.core.exception.BaseException;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.question.adaptor.FavorAnswerAdaptor;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.dto.condition.FavorRecommendationDTO;
import tify.server.domain.domains.question.exception.QuestionException;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FAACCRecommendationStrategy implements ProductRecommendationStrategy {

    private final ProductAdaptor productAdaptor;
    private final FavorAnswerAdaptor favorAnswerAdaptor;

    private static final int ANSWER_CNT = 1;
    private static final String CATEGORY_NAME = "FAACC";
    private final Map<String, Long> map = new ConcurrentHashMap<>();

    @Override
    public List<Product> recommendation(Long userId, String categoryName) {

        List<FavorRecommendationDTO> recommendationDTO = getRecommendationDTO(userId);

        validateAnswerSize(recommendationDTO);

        List<Product> firstFindProducts =
                firstStep(categoryName, recommendationDTO.get(0).getAnswer());

        return secondStep(firstFindProducts, recommendationDTO.get(1).getAnswer());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.valueOf(CATEGORY_NAME);
    }

    private List<FavorRecommendationDTO> getRecommendationDTO(Long userId) {

        map.put("목걸이", 1L);
        map.put("반지", 2L);
        map.put("귀걸이", 3L);
        map.put("팔찌", 4L);

        List<FavorAnswer> favorAnswers = new ArrayList<>();

        FavorAnswer initFavorAnswer =
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 5L);
        favorAnswers.add(initFavorAnswer);

        List<String> split = Arrays.stream(initFavorAnswer.getAnswerContent().split(", ")).toList();
        split.forEach(
                s -> {
                    favorAnswers.add(
                            favorAnswerAdaptor.searchByCategoryNameAndNumber(
                                    userId, CATEGORY_NAME, map.get(s)));
                });

        return favorAnswers.stream().map(FavorRecommendationDTO::from).toList();
    }

    private List<Product> firstStep(String categoryName, String answer) {
        return productAdaptor.queryAllByCategoryNameAndCharacter(categoryName, answer);
    }

    private List<Product> secondStep(List<Product> products, String answer) {
        return products.stream()
                .filter(product -> product.getCharacteristic().contains(answer))
                .toList();
    }

    private void validateAnswerSize(List<FavorRecommendationDTO> dto) {
        if (dto.size() != ANSWER_CNT) {
            throw new BaseException(QuestionException.FAVOR_ANSWER_SIZE_NOT_MATCHED_CATEGORY_ERROR);
        }
    }
}
