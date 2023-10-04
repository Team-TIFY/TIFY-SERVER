package tify.server.domain.domains.question.strategy;


import java.util.List;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.question.dto.condition.FavorRecommendationDTO;

public interface ProductRecommendationStrategy {

    public List<Product> recommendation(
            Long userId, String categoryName, List<FavorRecommendationDTO> dto);
}
