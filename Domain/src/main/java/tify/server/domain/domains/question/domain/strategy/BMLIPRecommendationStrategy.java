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
public class BMLIPRecommendationStrategy implements ProductRecommendationStrategy {

    private final ProductAdaptor productAdaptor;

    private static final int MINIMUM_COUNT = 12;
    private static final int ANSWER_CNT = 3;

    @Override
    public List<Product> recommendation(String categoryName, List<FavorRecommendationDTO> dto) {

        // 개수검증
        validateAnswerSize(dto);

        // 1번 스텝
        List<Product> firstStepProducts = firstStep(categoryName, dto.get(0).getAnswer());
        if (validateProductCount(firstStepProducts)) {
            return firstStepProducts;
        }

        // 2번 스텝
        List<Product> secondStepProducts = otherStep(firstStepProducts, dto.get(1).getAnswer());
        if (validateProductCount(secondStepProducts)) {
            return secondStepProducts;
        }

        // 3번 스텝
        List<Product> thirdStepProducts = otherStep(secondStepProducts, dto.get(2).getAnswer());

        return thirdStepProducts;
    }

    private List<Product> firstStep(String categoryName, String answer) {
        return productAdaptor.queryAllByCategoryNameAndCharacter(categoryName, answer);
    }

    private List<Product> otherStep(List<Product> findProducts, String answer) {
        List<String> answerSplits = Arrays.stream(answer.split(", ")).toList();
        return findProducts.stream()
                .filter(
                        product -> {
                            if (answerSplits.size() == 1) {
                                return product.getCharacteristic().contains(answerSplits.get(0));
                            } else if (answerSplits.size() == 2) {
                                return product.getCharacteristic().contains(answerSplits.get(0))
                                        && product.getCharacteristic()
                                                .contains(answerSplits.get(1));
                            } else {
                                return true;
                            }
                        })
                .toList();
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
