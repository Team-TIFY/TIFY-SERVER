package tify.server.domain.domains.question.domain.strategy;


import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.question.dto.condition.FavorRecommendationDTO;

@RequiredArgsConstructor
public class BMLIPRecommendationStrategy implements ProductRecommendationStrategy {

    private final ProductAdaptor productAdaptor;

    @Override
    public List<Product> recommendation(String categoryName, List<FavorRecommendationDTO> dto) {
        return null;
    }

    private List<Product> firstStep(FavorRecommendationDTO dto) {
        return null;
    }
}
