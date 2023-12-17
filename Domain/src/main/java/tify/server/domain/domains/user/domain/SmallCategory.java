package tify.server.domain.domains.user.domain;

import static tify.server.domain.domains.user.domain.LargeCategory.BEAUTY;
import static tify.server.domain.domains.user.domain.LargeCategory.FASHION;
import static tify.server.domain.domains.user.domain.LargeCategory.HOBBY;

import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SmallCategory {
    MAKEUP("메이크업", BEAUTY),
    FRAGRANCE("프레그런스", BEAUTY),
    CLOTHES("의류", FASHION),
    FASHION_PRODUCT("패션 소품", FASHION),
    DIGITAL_PRODUCT("디지털 소품", FASHION),
    BAG("가방", FASHION),
    ACCESSORY("악세서리", FASHION),
    COOKING("요리", HOBBY),
    EXERCISE("운동", HOBBY),
    TRAVEL("여행", HOBBY),
    CULTURE_LIFE("문화생활", HOBBY),
    ;

    public static List<SmallCategory> getSmallCategories() {
        return Arrays.stream(SmallCategory.values()).toList();
    }

    final String value;
    final LargeCategory largeCategory;
}
