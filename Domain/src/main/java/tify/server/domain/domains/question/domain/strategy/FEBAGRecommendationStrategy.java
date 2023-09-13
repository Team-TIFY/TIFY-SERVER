package tify.server.domain.domains.question.domain.strategy;


import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.core.exception.BaseException;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.question.dto.condition.FavorRecommendationDTO;
import tify.server.domain.domains.question.exception.QuestionException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FEBAGRecommendationStrategy implements ProductRecommendationStrategy {

    private final ProductAdaptor productAdaptor;

    private static final int MINIMUM_COUNT = 12;
    private static final int ANSWER_CNT = 3;

    @Override
    public List<Product> recommendation(String categoryName, List<FavorRecommendationDTO> dto) {
        validateAnswerSize(dto);

        List<Product> firstProducts = firstStep(categoryName, dto.get(0).getAnswer());
        List<Product> secondProducts = secondStep(firstProducts, dto.get(1).getAnswer());

        if (validateProductCount(secondProducts)) {
            return secondProducts;
        }
        return secondStep(secondProducts, dto.get(2).getAnswer());
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
