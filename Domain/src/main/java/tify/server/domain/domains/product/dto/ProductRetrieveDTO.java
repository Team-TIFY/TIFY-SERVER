package tify.server.domain.domains.product.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.product.domain.Product;

@Getter
@AllArgsConstructor
@Builder
public class ProductRetrieveDTO {

    private Long productId;
    private String name;
    private String brand;
    private String characteristic;
    private Long price;
    private String productOption;
    private String crawlUrl;
    private String siteUrl;

    public static ProductRetrieveDTO from(Product product) {
        return ProductRetrieveDTO.builder()
                .productId(product.getId())
                .name(product.getName())
                .brand(product.getBrand())
                .characteristic(product.getCharacteristic())
                .price(product.getPrice())
                .productOption(product.getProductOption())
                .crawlUrl(product.getCrawlUrl())
                .siteUrl(product.getCrawlUrl())
                .build();
    }
}
