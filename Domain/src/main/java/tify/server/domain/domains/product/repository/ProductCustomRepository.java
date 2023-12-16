package tify.server.domain.domains.product.repository;


import java.util.List;
import org.springframework.data.domain.Slice;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.product.domain.Site;
import tify.server.domain.domains.product.dto.ProductCategoryCondition;
import tify.server.domain.domains.product.dto.ProductCondition;
import tify.server.domain.domains.product.dto.ProductCrawlingDto;
import tify.server.domain.domains.product.dto.ProductRetrieveDTO;

public interface ProductCustomRepository {
    List<ProductCrawlingDto> search();

    List<ProductCrawlingDto> searchByCompany(Site site);

    List<Product> searchAllToRecommendation(String categoryName, String answer);

    Slice<ProductRetrieveDTO> searchByKeyword(ProductCondition productCondition);

    Slice<ProductRetrieveDTO> searchBySmallCategory(
            ProductCategoryCondition productCategoryCondition);

    List<ProductRetrieveDTO> findAllBySmallCategory(
            ProductCategoryCondition productCategoryCondition);
}
