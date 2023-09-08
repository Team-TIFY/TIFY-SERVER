package tify.server.domain.domains.user.domain;

import static tify.server.domain.domains.user.domain.SmallCategory.CLOTHES;
import static tify.server.domain.domains.user.domain.SmallCategory.FASHION_PRODUCT;
import static tify.server.domain.domains.user.domain.SmallCategory.FRAGRANCE;
import static tify.server.domain.domains.user.domain.SmallCategory.MAKEUP;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DetailCategory {
    LIP("립", MAKEUP),
    EYE("아이", MAKEUP),
    PERFUME("향수", FRAGRANCE),
    MOISTURE("보습겸용", FRAGRANCE),
    PLACE("공간향", FRAGRANCE),
    TOP("상의", CLOTHES),
    FAS_PRODUCT("패션소품", FASHION_PRODUCT),
    DIG_PRODUCT("디지털/테크소품", FASHION_PRODUCT),
    BAG("가방", SmallCategory.BAG),
    ;

    public static List<DetailCategory> getDetailCategories() {
        return Arrays.stream(DetailCategory.values()).toList();
    }

    final String value;
    final SmallCategory smallCategory;
}
