package tify.server.domain.common.vo;


import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.Gender;
import tify.server.domain.domains.user.domain.Profile;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserOnBoardingStatus;

@Getter
@Builder
public class UserProfileVo {

    private final String userName;

    private final String userId;

    private final String email;

    private final String thumbnail;

    private final String birth;

    private final String job;

    private final String gender;

    private final String onBoardingStatus;

    public static UserProfileVo from(User user) {
        return UserProfileVo.builder()
                .userName(
                        Optional.ofNullable(user.getProfile())
                                .map(Profile::getUserName)
                                .orElse(null))
                .userId(user.getUserId())
                .email(Optional.ofNullable(user.getProfile()).map(Profile::getEmail).orElse(null))
                .thumbnail(
                        Optional.ofNullable(user.getProfile())
                                .map(Profile::getThumbNail)
                                .orElse(null))
                .birth(Optional.ofNullable(user.getProfile()).map(Profile::getBirth).orElse(null))
                .job(Optional.ofNullable(user.getProfile()).map(Profile::getJob).orElse(null))
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
