package tify.server.domain.domains.user.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SmallCategory {
    SPICY("spicy", "PERFUME");

    final String value;
    final String largeCategory;
}
