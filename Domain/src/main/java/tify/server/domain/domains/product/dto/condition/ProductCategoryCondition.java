package tify.server.domain.domains.product.dto.condition;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;
import tify.server.domain.domains.product.domain.PriceFilter;
import tify.server.domain.domains.product.domain.PriceOrder;

@Getter
@AllArgsConstructor
public class ProductCategoryCondition {

    private List<Long> categoryIdList;

    private PriceOrder priceOrder;

    private PriceFilter priceFilter;

    private Pageable pageable;
}
