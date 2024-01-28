package tify.server.domain.domains.product.dto.model;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ProductCrawlingDto {

    private final String name;
    private final String crawlUrl;

    @QueryProjection
    public ProductCrawlingDto(String name, String crawlUrl) {
        this.name = name;
        this.crawlUrl = crawlUrl;
    }
}
