package tify.server.domain.domains.product.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.product.domain.Site;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductCustomRepository {

    List<Product> findAllByName(String name);

    List<Product> findAllBySite(Site site);
}
