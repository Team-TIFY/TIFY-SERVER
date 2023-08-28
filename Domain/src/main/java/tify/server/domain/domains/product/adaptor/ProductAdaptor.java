package tify.server.domain.domains.product.adaptor;


import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.product.domain.Site;
import tify.server.domain.domains.product.dto.ProductCrawlingDto;
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

    public List<Product> queryAllBySite(Site site) {
        return productRepository.findAllBySite(site);
    }
}