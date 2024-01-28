package tify.server.domain.domains.product.dto.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.product.domain.Product;

@Getter
@AllArgsConstructor
@Builder
public class ProductVo {

    //    product.id,
    //    product.name,
    //    product.brand,
    //    product.characteristic,
    //    product.price,
    //    product.productOption,
    //    product.imageUrl,
    //    product.crawlUrl

    private Long id;

    private String name;

    private String brand;

    private String characteristic;

    private Long price;

    private String productOption;

    private String imageUrl;

    private String crawlUrl;

    public static ProductVo from(Product product) {
        return ProductVo.builder()
                .id(product.getId())
                .name(product.getName())
                .brand(product.getBrand())
                .characteristic(product.getCharacteristic())
                .price(product.getPrice())
                .productOption(product.getProductOption())
                .imageUrl(product.getImageUrl())
                .crawlUrl(product.getCrawlUrl())
                .build();
    }
}
