package tify.server.api.user.model.dto.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.User;

@Getter
@Builder
public class UserSearchInfoVo {

    @Schema(description = "검색하여 찾은 유저의 pk")
    private final Long id;

    @Schema(description = "검색하여 찾은 유저의 id")
    private final String userId;

    @Schema(description = "검색하여 찾은 유저의 이름")
    private final String userName;

    @Schema(description = "검색하여 찾은 유저의 프로필 이미지")
    private final String imgUrl;

    @Schema(description = "검색하여 찾은 친구와 함께 아는 친구 수")
    private final int mutualFriends;

    @Schema(description = "검색하여 찾은 친구와의 친구 여부")
    private final boolean isFriend;

    public static UserSearchInfoVo of(User user, int mutualFriends, boolean isFriend) {
        return UserSearchInfoVo.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .userName(user.getProfile().getUserName())
                .imgUrl(user.getProfile().getThumbNail())
                .mutualFriends(mutualFriends)
                .isFriend(isFriend)
                .build();
    }
}
