package tify.server.domain.domains.product.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
public class ProductCategoryCondition {

    private Long categoryId;
    private Pageable pageable;
}
