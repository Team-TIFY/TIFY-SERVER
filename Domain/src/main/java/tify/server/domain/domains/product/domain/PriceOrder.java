package tify.server.domain.domains.product.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PriceOrder {
    DEFAULT,
    PRICE_ASC,
    PRICE_DESC;
}
