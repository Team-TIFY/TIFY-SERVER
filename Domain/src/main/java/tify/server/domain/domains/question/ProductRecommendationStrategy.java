package tify.server.domain.domains.question;


import java.util.List;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.question.dto.condition.FavorRecommendationDTO;

public interface ProductRecommendationStrategy {

    public List<Product> recommendation(String categoryName, List<FavorRecommendationDTO> dto);
}
