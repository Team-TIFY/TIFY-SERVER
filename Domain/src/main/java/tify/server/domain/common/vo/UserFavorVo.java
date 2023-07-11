package tify.server.domain.common.vo;


import java.util.List;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.UserFavor;
import tify.server.domain.domains.user.domain.UserTag;

@Getter
@Builder
public class UserFavorVo {

    private final Long userId;

    private final Long userTagId;

    private final Long largetCategoryId;

    //  private final Long smallCategoryId;

    //  private final String thumbNailImageUrl;

    private final List<UserFavor> favors;

    @Builder
    public static UserFavorVo from(UserTag userTag) {
        return UserFavorVo.builder()
                .userId(userTag.getId())
                .userTagId(userTag.getId())
                .largetCategoryId(userTag.getLargeCategoryId())
                .favors(userTag.getFavors())
                .build();
    }
}
