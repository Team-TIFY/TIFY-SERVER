package tify.server.domain.domains.product.adaptor;


import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.product.entity.Product;
import tify.server.domain.domains.product.repository.ProductRepository;

@Adaptor
@RequiredArgsConstructor
public class ProductAdaptor {

    private final ProductRepository productRepository;

    public List<Product> queryAllByName(String name) {
        return productRepository.findAllByNameContains(name);
    }
}
