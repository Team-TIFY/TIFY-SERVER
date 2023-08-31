package tify.server.domain.domains.product.domain;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.AbstractTimeStamp;

@Getter
@Entity
@Table(name = "tbl_product")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long favorQuestionCategoryId;

    private String brand;

    private String name;

    @Enumerated(EnumType.STRING)
    private Site site;

    private String productOption;

    private String characteristic;

    private Long price;

    private String imageUrl;

    private String crawlUrl;

    @Builder
    public Product(
            Long favorQuestionCategoryId,
            String brand,
            String name,
            String option,
            String characteristic,
            Site site,
            Long price,
            String imageUrl,
            String crawlUrl) {
        this.favorQuestionCategoryId = favorQuestionCategoryId;
        this.brand = brand;
        this.name = name;
        this.site = site;
        this.productOption = option;
        this.characteristic = characteristic;
        this.price = price;
        this.imageUrl = imageUrl;
        this.crawlUrl = crawlUrl;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
