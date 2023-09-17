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
public class FAACCRecommendationStrategy implements ProductRecommendationStrategy {

    private final ProductAdaptor productAdaptor;

    private static final int ANSWER_CNT = 1;

    @Override
    public List<Product> recommendation(String categoryName, List<FavorRecommendationDTO> dto) {
        validateAnswerSize(dto);

        return firstStep(categoryName, dto.get(0).getAnswer());
    }

    private List<Product> firstStep(String categoryName, String answer) {
        return productAdaptor.queryAllByCategoryNameAndCharacter(categoryName, answer);
    }

    private void validateAnswerSize(List<FavorRecommendationDTO> dto) {
        if (dto.size() != ANSWER_CNT) {
            throw new BaseException(QuestionException.FAVOR_ANSWER_SIZE_NOT_MATCHED_CATEGORY_ERROR);
        }
    }
}
