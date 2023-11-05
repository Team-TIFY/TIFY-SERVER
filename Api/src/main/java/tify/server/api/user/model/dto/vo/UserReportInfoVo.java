package tify.server.api.user.model.dto.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.Profile;
import tify.server.domain.domains.user.domain.User;

@Getter
@Builder
public class UserReportInfoVo {

    @Schema(description = "신고당한 유저의 pk값입니다.")
    private final Long userId;

    @Schema(description = "신고당한 유저의 프로필입니다.")
    private final Profile profile;

    @Schema(description = "유저가 신고당한 횟수입니다.")
    private final int reportCount;

    public static UserReportInfoVo of(User user, int count) {
        return UserReportInfoVo.builder()
                .userId(user.getId())
                .profile(user.getProfile())
                .reportCount(count)
                .build();
    }
}
