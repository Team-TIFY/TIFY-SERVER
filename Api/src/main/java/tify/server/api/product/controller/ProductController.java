package tify.server.api.product.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tify.server.api.common.slice.SliceResponse;
import tify.server.api.product.model.dto.ProductFilterCondition;
import tify.server.api.product.service.CrawlingUseCase;
import tify.server.api.product.service.ProductSearchUseCase;
import tify.server.api.product.service.RetrieveProductListUseCase;
import tify.server.domain.domains.product.domain.PriceFilter;
import tify.server.domain.domains.product.domain.PriceOrder;
import tify.server.domain.domains.product.dto.ProductRetrieveDTO;
import tify.server.domain.domains.user.domain.SmallCategory;

@RestController
@Slf4j
@RequestMapping("/products")
@SecurityRequirement(name = "access-token")
@RequiredArgsConstructor
@Tag(name = "6. [상품]")
public class ProductController {

    private final CrawlingUseCase crawlingUseCase;
    private final ProductSearchUseCase productSearchUseCase;
    private final RetrieveProductListUseCase retrieveProductListUseCase;

    @Operation(summary = "올리브영 크롤링을 통해 상품명과 상품 이미지를 가져옵니다.")
    @PatchMapping("/oliveyoung")
    public void oliveYoungCrawl() {
        crawlingUseCase.executeForOliveYoung();
    }

    @Operation(summary = "무신사 크롤링을 통해 상품명과 상품 이미지를 가져옵니다.")
    @PatchMapping("/musinsa")
    public void musinsaCrawl() {
        crawlingUseCase.executeForMusinsa();
    }

    @Operation(summary = "29cm 크롤링을 통해 상품명과 상품 이미지를 가져옵니다.")
    @PatchMapping("/twentynine")
    public void twentynineCrawl() {
        crawlingUseCase.executeForTwentyNine();
    }

    @Operation(summary = "키워드를 이용하여 관련된 상품을 검색합니다.")
    @GetMapping()
    public SliceResponse<ProductRetrieveDTO> productSearch(
            @RequestParam String keyword,
            @ParameterObject @PageableDefault(size = 10) Pageable pageable) {
        return productSearchUseCase.execute(keyword, pageable);
    }

    @Operation(summary = "SmallCategory(FE기준 중분류) 별 상품을 조회합니다.")
    @GetMapping("/products/small-category")
    public SliceResponse<ProductRetrieveDTO> getCategoricalProduct(
            @Schema(description = "필터로 쓸 중분류입니다.") @RequestParam
                    List<SmallCategory> smallCategoryList,
            @Schema(description = "기본, 가격낮은순, 가격높은순 정렬입니다.", implementation = PriceOrder.class)
                    @RequestParam
                    PriceOrder priceOrder,
            @Schema(description = "가격대별 필터입니다.", implementation = PriceFilter.class) @RequestParam
                    PriceFilter priceFilter,
            @ParameterObject @PageableDefault(size = 10) Pageable pageable) {
        ProductFilterCondition productFilterCondition =
                new ProductFilterCondition(smallCategoryList, priceOrder, priceFilter);
        return retrieveProductListUseCase.executeToSmallCategory(
                productFilterCondition.getSmallCategoryList(),
                productFilterCondition.getPriceOrder(),
                productFilterCondition.getPriceFilter(),
                pageable);
    }
}
