package tify.server.domain.common.vo;


import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.Gender;
import tify.server.domain.domains.user.domain.User;

@Getter
@Builder
public class UserInfoVo {

    // 유저 프로필 사진, 이름, 생년월일, 직업 조회
    private final Long userId;

    private final String userName;

    private final String imageUrl;

    private final String birth;

    private final String job;

    private final LocalDateTime createdAt;

    private final String gender;

    public static UserInfoVo from(User user) {
        return UserInfoVo.builder()
                .userId(user.getId())
                .userName(user.getProfile().getUserName())
                .imageUrl(user.getProfile().getThumbNail())
                .birth(user.getProfile().getBirth())
                .job(user.getProfile().getJob())
                .createdAt(user.getCreatedAt().toLocalDateTime())
                .gender(Gender.toValue(user.getProfile().getGender()))
                .build();
    }
}
