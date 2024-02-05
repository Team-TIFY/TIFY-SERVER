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
public class BMLIPRecommendationStrategy implements ProductRecommendationStrategy {

    private final ProductAdaptor productAdaptor;
    private final FavorAnswerAdaptor favorAnswerAdaptor;

    private static final int MINIMUM_COUNT = 12;
    private static final int ANSWER_CNT = 3;
    private static final String CATEGORY_NAME = "BMLIP";

    @Override
    public List<Product> recommendation(Long userId, String categoryName) {

        List<FavorRecommendationDTO> recommendationDTO = getRecommendationDTO(userId);

        // 개수검증
        validateAnswerSize(recommendationDTO);

        // 1번 스텝
        List<Product> firstStepProducts =
                firstStep(categoryName, recommendationDTO.get(0).getAnswer());
        if (validateProductCount(firstStepProducts)) {
            return firstStepProducts;
        }

        // 2번 스텝
        List<Product> secondStepProducts =
                otherStep(firstStepProducts, recommendationDTO.get(1).getAnswer());
        if (validateProductCount(secondStepProducts)) {
            return secondStepProducts;
        }

        // 3번 스텝
        return otherStep(secondStepProducts, recommendationDTO.get(2).getAnswer());
    }

    @Override
    public StrategyName getStrategyName() {
        return StrategyName.valueOf(CATEGORY_NAME);
    }

    private List<FavorRecommendationDTO> getRecommendationDTO(Long userId) {
        List<FavorAnswer> favorAnswers = new ArrayList<>();
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 2L));
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 3L));
        favorAnswers.add(
                favorAnswerAdaptor.searchByCategoryNameAndNumber(userId, CATEGORY_NAME, 4L));
        return favorAnswers.stream().map(FavorRecommendationDTO::from).toList();
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
                                        || product.getCharacteristic()
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
