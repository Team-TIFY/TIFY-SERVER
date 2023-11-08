package tify.server.domain.domains.product.dto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
public class ProductCategoryCondition {

    private List<Long> categoryIdList;
    private Pageable pageable;
}
