package tify.server.domain.domains.user.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SmallCategory {
    SPICY("spicy", "PERFUME"),
    WOODY("woody", "PERFUME"), // FRAGRANCE로 업데이트 시 향수, 보습겸용, 공간향으로 업데이트 예정
    SUMMERCOOL("summercool", "MAEKUP"),
    PERIPERA("peripera", "MAKEUP"),
    CASUAL("casual", "CLOTHES"),
    FORMAL("formal", "CLOTHES");

    final String value;
    final String largeCategory;
}
