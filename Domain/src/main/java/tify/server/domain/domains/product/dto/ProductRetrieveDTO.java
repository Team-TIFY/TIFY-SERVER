package tify.server.domain.domains.product.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductRetrieveDTO {

    private String name;
    private String brand;
    private String characteristic;
    private Long price;
}
