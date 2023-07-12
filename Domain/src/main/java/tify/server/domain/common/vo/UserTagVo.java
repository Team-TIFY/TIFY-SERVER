package tify.server.domain.common.vo;


import java.util.List;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.UserTag;

@Getter
@Builder
public class UserTagVo {

    private final Long userTagId;

    private final String largeCategory;

    private final List<UserFavorVo> favors;

    public static UserTagVo from(UserTag userTag) {
        return UserTagVo.builder()
                .userTagId(userTag.getId())
                .largeCategory(userTag.getLargeCategory().getValue())
                .favors(userTag.getFavors().stream().map(UserFavorVo::from).toList())
                .build();
    }
}
