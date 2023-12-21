package tify.server.api.product.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import tify.server.domain.domains.product.dto.ProductRetrieveDTO;

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

    @Operation(summary = "SmallCategory(FE기준 중분류) 별 상품을 조회합니다. (무한스크롤 X)")
    @GetMapping("/products/small-category")
    public SliceResponse<ProductRetrieveDTO> getCategoricalProduct(
            @ParameterObject ProductFilterCondition productFilterCondition, Pageable pageable) {
        return retrieveProductListUseCase.executeToSmallCategory(productFilterCondition, pageable);
    }
}
