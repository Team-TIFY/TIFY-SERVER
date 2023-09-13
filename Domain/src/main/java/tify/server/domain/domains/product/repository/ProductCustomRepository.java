package tify.server.domain.domains.product.repository;


import java.util.List;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.product.dto.ProductCrawlingDto;

public interface ProductCustomRepository {
    List<ProductCrawlingDto> search();

    List<Product> searchAllToRecommendation(String categoryName, String answer);
}
