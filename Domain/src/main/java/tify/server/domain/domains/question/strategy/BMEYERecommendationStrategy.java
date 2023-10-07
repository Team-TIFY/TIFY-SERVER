package tify.server.domain.domains.question.strategy;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.core.exception.BaseException;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.question.adaptor.FavorAnswerAdaptor;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.dto.condition.FavorRecommendationDTO;
import tify.server.domain.domains.question.exception.QuestionException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BMEYERecommendationStrategy implements ProductRecommendationStrategy {

    private final ProductAdaptor productAdaptor;
    private final FavorAnswerAdaptor favorAnswerAdaptor;

    private static final int MINIMUM_COUNT = 12;
    private static final int ANSWER_CNT = 2;
    private static final String FIRST_CATEGORY_NAME = "BMLIP";
    private static final String SECOND_CATEGORY_NAME = "BMEYE";

    @Override
    public List<Product> recommendation(
            Long userId, String categoryName, List<FavorRecommendationDTO> dto) {

        List<FavorRecommendationDTO> recommendationDTO = getRecommendationDTO(userId);

        validateAnswerSize(recommendationDTO);

        List<Product> firstProducts = firstStep(categoryName, recommendationDTO.get(0).getAnswer());
        if (validateProductCount(firstProducts)) {
            return firstProducts;
        }

        return secondStep(firstProducts, recommendationDTO.get(1).getAnswer());
    }

    private List<FavorRecommendationDTO> getRecommendationDTO(Long userId) {
        List<FavorAnswer> favorAnswers = new ArrayList<>();
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, FIRST_CATEGORY_NAME, 2L));
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, SECOND_CATEGORY_NAME, 2L));
        return favorAnswers.stream().map(FavorRecommendationDTO::from).toList();
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
