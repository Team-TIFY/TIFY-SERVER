package tify.server.domain.domains.user.domain;

import static tify.server.domain.domains.user.domain.SmallCategory.FRAGRANCE;
import static tify.server.domain.domains.user.domain.SmallCategory.MAKEUP;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DetailCategory {
    LIP("립", MAKEUP),
    EYE("아이", MAKEUP),
    PERFUME("향수", FRAGRANCE),
    ;

    final String value;
    final SmallCategory smallCategory;
}
