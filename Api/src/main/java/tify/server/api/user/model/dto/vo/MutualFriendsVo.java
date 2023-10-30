package tify.server.api.user.model.dto.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MutualFriendsVo {

    @Schema(description = "현재 내 id")
    private final Long fromUserId;

    @Schema(description = "검색으로 찾은 유저의 id")
    private final Long toUserId;

    @Schema(description = "함께 아는 친구의 수")
    private final int mutualFriendsCount;

    public static MutualFriendsVo of(Long fromUserId, Long toUserId, int mutualFriendsCount) {
        return MutualFriendsVo.builder()
                .fromUserId(fromUserId)
                .toUserId(toUserId)
                .mutualFriendsCount(mutualFriendsCount)
                .build();
    }
}
