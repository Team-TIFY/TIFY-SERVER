package tify.server.domain.domains.product.domain;

import static java.lang.Long.*;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PriceFilter {
    DEFAULT(0L, MAX_VALUE),
    LESS_THAN_10000(0L, 9999L),
    MORE_THAN_10000_LESS_THAN_30000(10000L, 29999L),
    MORE_THAN_30000_LESS_THAN_50000(30000L, 49999L),
    MORE_THAN_50000(50000L, MAX_VALUE);

    final Long start;
    final Long end;
}
