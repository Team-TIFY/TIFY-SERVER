package tify.server.domain.domains.question.strategy;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tify.server.domain.domains.user.domain.SmallCategory;

@Component
@RequiredArgsConstructor
public class ProductRecommendationStrategyFactory {

    private final List<ProductRecommendationStrategy> strategies;

    public List<ProductRecommendationStrategy> findStrategy(SmallCategory smallCategory) {
        return strategies.stream()
                .filter(
                        strategy ->
                                strategy.getStrategyName()
                                        .getDetailCategory()
                                        .getSmallCategory()
                                        .equals(smallCategory))
                .toList();
    }
}
