package tify.server.domain.common.vo;


import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.UserFavor;

@Getter
@Builder
public class UserFavorVo {

    private final Long userFavorId;

    private final String smallCategory;

    public static UserFavorVo from(UserFavor userFavor) {
        return UserFavorVo.builder()
                .userFavorId(userFavor.getId())
                .smallCategory(userFavor.getSmallCategory().getValue())
                .build();
    }
}
