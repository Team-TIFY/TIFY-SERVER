package tify.server.domain.domains.user.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LargeCategory {
    PERFUME("향"), // FRAGRANCE로 업데이트 예정
    MAKEUP("메이크업"),
    CLOTHES("의류"),
    FASHIONSTUFF("패션잡화"),
    ACCESSORY("액세서리"),
    COOKING("요리"),
    SPORTS("운동"),
    TRIP("여행"),
    CULTURALLIFE("문화생활"),
    MUSIC("음악");

    final String value;
}
