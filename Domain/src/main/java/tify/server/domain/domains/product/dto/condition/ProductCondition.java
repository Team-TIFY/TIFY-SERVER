package tify.server.domain.domains.product.dto.condition;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
public class ProductCondition {

    private String keyword;
    private Pageable pageable;
}
