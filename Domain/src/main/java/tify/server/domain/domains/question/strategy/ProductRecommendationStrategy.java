package tify.server.domain.domains.question.strategy;


import java.util.List;
import tify.server.domain.domains.product.domain.Product;

public interface ProductRecommendationStrategy {

    public List<Product> recommendation(Long userId, String categoryName);

    public StrategyName getStrategyName();
}
