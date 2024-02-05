package tify.server.domain.domains.question.strategy;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.question.adaptor.FavorAnswerAdaptor;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.dto.condition.FavorRecommendationDTO;

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

        //        validateAnswerSize(recommendationDTO);

        List<Product> firstStep = firstStep(categoryName, recommendationDTO.get(0).getAnswer());

        if (recommendationDTO.size() == 2) { // 액서서리의 종류를 한가지만 고르면 size가 2
            return getConditionalProducts(firstStep, recommendationDTO.get(1).getAnswer());
        } else { // 아니라면 3
            List<String> answers =
                    List.of(
                            recommendationDTO.get(1).getAnswer(),
                            recommendationDTO.get(2).getAnswer());
            return secondStep(firstStep, answers);
        }
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
        String[] splitAnswer = answer.split(", ");
        if (splitAnswer.length == 1) {
            return productAdaptor.queryAllByCategoryNameAndCharacter(categoryName, splitAnswer[0]);
        } else {
            return Stream.concat(
                            productAdaptor
                                    .queryAllByCategoryNameAndCharacter(
                                            categoryName, splitAnswer[0])
                                    .stream(),
                            productAdaptor
                                    .queryAllByCategoryNameAndCharacter(
                                            categoryName, splitAnswer[1])
                                    .stream())
                    .toList();
        }
    }

    private List<Product> secondStep(List<Product> productList, List<String> answers) {
        List<Product> result = new ArrayList<>();
        answers.forEach(answer -> result.addAll(getConditionalProducts(productList, answer)));
        return result;
    }

    private List<Product> getConditionalProducts(List<Product> productList, String answer) {
        String[] splitAnswer = answer.split(", ");
        if (splitAnswer.length == 1) {
            return productList.stream()
                    .filter(product -> product.getCharacteristic().contains(answer))
                    .toList();
        } else {
            return productList.stream()
                    .filter(
                            product ->
                                    product.getCharacteristic().contains(splitAnswer[0])
                                            || product.getCharacteristic().contains(splitAnswer[1]))
                    .toList();
        }
    }

    //    private void validateAnswerSize(List<FavorRecommendationDTO> dto) {
    //        if (dto.size() != ANSWER_CNT) {
    //            throw new
    // BaseException(QuestionException.FAVOR_ANSWER_SIZE_NOT_MATCHED_CATEGORY_ERROR);
    //        }
    //    }
}
