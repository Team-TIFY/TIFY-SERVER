package tify.server.domain.domains.question.strategy;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
public class FEBAGRecommendationStrategy implements ProductRecommendationStrategy {

    private final ProductAdaptor productAdaptor;
    private final FavorAnswerAdaptor favorAnswerAdaptor;

    private static final int MINIMUM_COUNT = 12;
    private static final int ANSWER_CNT = 3;
    private static final String CATEGORY_NAME = "FEBAG";

    @Override
    public List<Product> recommendation(Long userId, String categoryName) {

        List<FavorRecommendationDTO> recommendationDTO = getRecommendationDTO(userId);

        validateAnswerSize(recommendationDTO);

        List<Product> firstProducts = firstStep(categoryName, recommendationDTO.get(0).getAnswer());
        List<Product> secondProducts =
                secondStep(firstProducts, recommendationDTO.get(1).getAnswer());

        if (validateProductCount(secondProducts)) {
            return secondProducts;
        }
        return secondStep(secondProducts, recommendationDTO.get(2).getAnswer());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.valueOf(CATEGORY_NAME);
    }

    private List<FavorRecommendationDTO> getRecommendationDTO(Long userId) {
        List<FavorAnswer> favorAnswers = new ArrayList<>();
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 3L));
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 1L));
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 2L));
        return favorAnswers.stream().map(FavorRecommendationDTO::from).toList();
    }

    private List<Product> firstStep(String categoryName, String answer) {
        List<String> answerSplits = Arrays.stream(answer.split(", ")).toList();

        if (answerSplits.size() == 1) {
            return productAdaptor.queryAllByCategoryNameAndCharacter(
                    categoryName, answerSplits.get(0));
        } else {
            List<Product> findProducts =
                    productAdaptor.queryAllByCategoryNameAndCharacter(
                            categoryName, answerSplits.get(0));
            return findProducts.stream()
                    .filter(product -> product.getCharacteristic().contains(answerSplits.get(1)))
                    .toList();
        }
    }

    private List<Product> secondStep(List<Product> findProducts, String answer) {
        List<String> answerSplits = Arrays.stream(answer.split(", ")).toList();

        if (answerSplits.size() == 1) {
            return findProducts.stream()
                    .filter(product -> product.getCharacteristic().contains(answerSplits.get(0)))
                    .toList();
        } else {
            return findProducts.stream()
                    .filter(
                            product ->
                                    product.getCharacteristic().contains(answerSplits.get(0))
                                            || product.getCharacteristic()
                                                    .contains(answerSplits.get(1)))
                    .toList();
        }
    }

    private boolean validateProductCount(List<Product> products) {
        return products.size() <= MINIMUM_COUNT;
    }

    private void validateAnswerSize(List<FavorRecommendationDTO> dto) {
        if (dto.size() != ANSWER_CNT) {
            throw new BaseException(QuestionException.FAVOR_ANSWER_SIZE_NOT_MATCHED_CATEGORY_ERROR);
        }
    }
}
