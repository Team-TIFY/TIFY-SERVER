package tify.server.api.product.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.product.dto.ProductRetrieveDTO;
import tify.server.domain.domains.user.domain.DetailCategory;
import tify.server.domain.domains.user.domain.LargeCategory;
import tify.server.domain.domains.user.domain.SmallCategory;

@Getter
@Builder
public class ProductRetrieveVo {

    @Schema(description = "상품의 pk 값입니다.")
    private final Long productId;

    @Schema(description = "상품의 이름입니다.")
    private final String name;

    @Schema(description = "상품의 브랜드입니다.")
    private final String brand;

    @Schema(description = "상품의 속성입니다.", example = "포근한")
    private final String characteristic;

    @Schema(description = "상품의 가격입니다.")
    private final Long price;

    @Schema(description = "상품의 옵션입니다.", example = "브라운, 옐로우")
    private final String productOption;

    @Schema(description = "상품의 이미지 url입니다.")
    private final String imageUrl;

    @Schema(description = "상품의 구매 링크입니다.")
    private final String siteUrl;

    @Schema(description = "상품의 대분류입니다.", implementation = LargeCategory.class)
    private final LargeCategory largeCategory;

    @Schema(description = "상품의 중분류입니다.", implementation = SmallCategory.class)
    private final SmallCategory smallCategory;

    @Schema(description = "상품의 소분류입니다.", implementation = DetailCategory.class)
    private final DetailCategory detailCategory;

    @Schema(description = "상품 질문의 카테고리 이름입니다.", example = "BMLIP")
    private final String categoryName;

    public static ProductRetrieveVo from(ProductRetrieveDTO dto) {
        return ProductRetrieveVo.builder()
            .productId(dto.getProduct().getId())
            .name(dto.getProduct().getName())
            .brand(dto.getProduct().getBrand())
            .characteristic(dto.getProduct().getCharacteristic())
            .price(dto.getProduct().getPrice())
            .productOption(dto.getProduct().getProductOption())
            .imageUrl(dto.getProduct().getImageUrl())
            .siteUrl(dto.getProduct().getCrawlUrl())
            .largeCategory(dto.getFavorQuestionCategory().getLargeCategory())
            .smallCategory(dto.getFavorQuestionCategory().getSmallCategory())
            .detailCategory(dto.getFavorQuestionCategory().getDetailCategory())
            .categoryName(dto.getFavorQuestionCategory().getName())
            .build();
    }
}