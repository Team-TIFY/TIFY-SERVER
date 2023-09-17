package tify.server.domain.domains.question.domain.strategy;


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
public class BMEYERecommendationStrategy implements ProductRecommendationStrategy {

    private final ProductAdaptor productAdaptor;

    private static final int MINIMUM_COUNT = 12;
    private static final int ANSWER_CNT = 2;

    @Override
    public List<Product> recommendation(String categoryName, List<FavorRecommendationDTO> dto) {
        validateAnswerSize(dto);

        List<Product> firstProducts = firstStep(categoryName, dto.get(0).getAnswer());
        if (validateProductCount(firstProducts)) {
            return firstProducts;
        }

        return secondStep(firstProducts, dto.get(1).getAnswer());
    }

    private List<Product> firstStep(String categoryName, String answer) {
        return productAdaptor.queryAllByCategoryNameAndCharacter(categoryName, answer);
    }

    private List<Product> secondStep(List<Product> findProducts, String answer) {
        return findProducts.stream()
                .filter(product -> product.getCharacteristic().contains(answer))
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
