package tify.server.domain.domains.product.domain;

import static java.lang.Long.*;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PriceFilter {
    DEFAULT(0L, MAX_VALUE),
    LESS_THAN_10000(0L, 10000L),
    MORE_THAN_10000_LESS_THAN_20000(10000L, 20000L),
    MORE_THAN_20000_LESS_THAN_30000(20000L, 30000L),
    MORE_THAN_30000_LESS_THAN_40000(30000L, 40000L),
    MORE_THAN_40000_LESS_THAN_50000(40000L, 50000L),
    MORE_THAN_50000(50000L, MAX_VALUE);

    final Long start;
    final Long end;
}
