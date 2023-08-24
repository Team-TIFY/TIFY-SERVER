package tify.server.domain.domains.product.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByNameContains(String name);
}
