package tify.server.api.product.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import tify.server.domain.domains.product.domain.PriceFilter;
import tify.server.domain.domains.product.domain.PriceOrder;
import tify.server.domain.domains.user.domain.SmallCategory;

@Data
public class ProductFilterCondition {

    @Schema(description = "필터로 쓸 중분류입니다.")
    private final List<SmallCategory> smallCategoryList;

    @Schema(description = "기본, 가격낮은순, 가격높은순 정렬입니다.", implementation = PriceOrder.class)
    private final PriceOrder priceOrder;

    @Schema(description = "가격대별 필터입니다.", implementation = PriceFilter.class)
    private final PriceFilter priceFilter;
}
