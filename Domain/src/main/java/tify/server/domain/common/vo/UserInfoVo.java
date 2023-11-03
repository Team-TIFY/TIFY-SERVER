package tify.server.domain.common.vo;


import java.time.LocalDateTime;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.Gender;
import tify.server.domain.domains.user.domain.Profile;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserOnBoardingStatus;

@Getter
@Builder
public class UserInfoVo {

    // 유저 프로필 사진, 이름, 생년월일, 직업 조회
    private final Long id;

    private final String userName;

    private final String userId;

    private final String imageUrl;

    private final String birth;

    private final String job;

    private final LocalDateTime createdAt;

    private final String gender;

    private final String onBoardingStatus;

    public static UserInfoVo from(User user) {
        return UserInfoVo.builder()
                .id(user.getId())
                .userName(
                        Optional.ofNullable(user.getProfile())
                                .map(Profile::getUserName)
                                .orElse(null))
                .userId(user.getUserId())
                .imageUrl(
                        Optional.ofNullable(user.getProfile())
                                .map(Profile::getThumbNail)
                                .orElse(null))
                .birth(Optional.ofNullable(user.getProfile()).map(Profile::getBirth).orElse(null))
                .job(Optional.ofNullable(user.getProfile()).map(Profile::getJob).orElse(null))
                .createdAt(user.getCreatedAt().toLocalDateTime())
                .gender(
                        Gender.toValue(
                                Optional.ofNullable(user.getProfile())
                                        .map(Profile::getGender)
                                        .orElse(null)))
                .onBoardingStatus(
                        Optional.ofNullable(user.getOnBoardingStatus())
                                .map(UserOnBoardingStatus::getName)
                                .orElse(null))
                .build();
    }
}
