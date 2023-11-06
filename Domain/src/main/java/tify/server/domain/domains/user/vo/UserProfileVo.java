package tify.server.domain.domains.user.vo;


import java.util.Optional;
import lombok.Builder;
import lombok.Data;
import tify.server.domain.domains.user.domain.Gender;
import tify.server.domain.domains.user.domain.Profile;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserOnBoardingStatus;

@Data
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

    private boolean isFriend;

    private boolean isBlocked;

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

    public static UserProfileVo of(User user, boolean isFriend, boolean isBlocked) {
        UserProfileVo result = from(user);
        result.setFriend(isFriend);
        result.setBlocked(isBlocked);
        return result;
    }
}
