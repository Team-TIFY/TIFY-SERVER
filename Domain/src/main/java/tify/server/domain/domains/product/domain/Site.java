package tify.server.domain.domains.product.domain;


import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Site {
    OLIVE_YOUNG("oliveyoung"),
    MUSINSA("musinsa"),
    CM("29cm"),
    ;

    final String value;

    public static Site toEnum(String value) {
        return Arrays.stream(values())
                .filter(s -> s.toString().equals(value))
                .findAny()
                .orElseThrow();
    }
}
