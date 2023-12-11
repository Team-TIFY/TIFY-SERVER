package tify.server.domain.domains.user.vo;


import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.LargeCategory;
import tify.server.domain.domains.user.domain.UserFavor;

@Getter
@Builder
public class UserFavorVo {

    private final UserFavor userFavor;

    private final LargeCategory largeCategory;

    public static UserFavorVo from(UserFavor userFavor) {
        return UserFavorVo.builder()
                .userFavor(userFavor)
                .largeCategory(userFavor.getDetailCategory().getSmallCategory().getLargeCategory())
                .build();
    }
}
