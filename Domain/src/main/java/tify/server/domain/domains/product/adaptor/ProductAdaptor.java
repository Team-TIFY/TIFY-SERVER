package tify.server.domain.domains.product.adaptor;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.product.domain.Site;
import tify.server.domain.domains.product.dto.ProductCategoryCondition;
import tify.server.domain.domains.product.dto.ProductCondition;
import tify.server.domain.domains.product.dto.ProductCrawlingDto;
import tify.server.domain.domains.product.dto.ProductRetrieveDTO;
import tify.server.domain.domains.product.repository.ProductRepository;

@Adaptor
@RequiredArgsConstructor
public class ProductAdaptor {

    private final ProductRepository productRepository;

    public List<Product> queryAllByName(String name) {
        return productRepository.findAllByName(name);
    }

    public List<ProductCrawlingDto> searchByName() {
        return productRepository.search();
    }

    public List<ProductCrawlingDto> searchByCompany(Site site) {
        return productRepository.searchByCompany(site);
    }

    public List<Product> queryAllBySite(Site site) {
        return productRepository.findAllBySite(site);
    }

    public List<Product> queryAllByCategoryNameAndCharacter(String categoryName, String character) {
        return productRepository.searchAllToRecommendation(categoryName, character);
    }

    public Slice<ProductRetrieveDTO> searchByKeyword(ProductCondition productCondition) {
        return productRepository.searchByKeyword(productCondition);
    }

    public Slice<ProductRetrieveDTO> searchBySmallCategoryId(
            ProductCategoryCondition productCategoryCondition) {
        return productRepository.searchBySmallCategory(productCategoryCondition);
    }

    public List<ProductRetrieveDTO> findAllBySmallCategoryId(
            ProductCategoryCondition productCategoryCondition) {
        return productRepository.findAllBySmallCategory(productCategoryCondition);
    }

    public List<Product> queryAllByCategoryName(String categoryName) {
        return productRepository.searchAllByCategoryName(categoryName);
    }
}
