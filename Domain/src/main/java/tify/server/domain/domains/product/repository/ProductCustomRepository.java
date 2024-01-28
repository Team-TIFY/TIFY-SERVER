package tify.server.domain.domains.product.repository;


import java.util.List;
import org.springframework.data.domain.Slice;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.product.domain.Site;
import tify.server.domain.domains.product.dto.condition.ProductCategoryCondition;
import tify.server.domain.domains.product.dto.condition.ProductCondition;
import tify.server.domain.domains.product.dto.model.ProductCrawlingDto;
import tify.server.domain.domains.product.dto.model.ProductRetrieveDto;
import tify.server.domain.domains.product.dto.model.ProductVo;

public interface ProductCustomRepository {
    List<ProductCrawlingDto> search();

    List<ProductCrawlingDto> searchByCompany(Site site);

    List<Product> searchAllToRecommendation(String categoryName, String answer);

    Slice<ProductVo> searchByKeyword(ProductCondition productCondition);

    Slice<ProductRetrieveDto> searchBySmallCategory(
            ProductCategoryCondition productCategoryCondition);

    List<ProductRetrieveDto> findAllBySmallCategory(
            ProductCategoryCondition productCategoryCondition);

    List<Product> searchAllByCategoryName(String categoryName);
}
