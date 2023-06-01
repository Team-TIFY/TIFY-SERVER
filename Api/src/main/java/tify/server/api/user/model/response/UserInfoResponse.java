package tify.server.api.user.model.response;


import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.OauthProvider;
import tify.server.domain.domains.user.domain.Profile;
import tify.server.domain.domains.user.domain.User;

@Getter
@Builder
public class UserInfoResponse {

    private Long userId;
    private Profile profile;
    private OauthProvider provider;

    public static UserInfoResponse from(User user) {
        return UserInfoResponse.builder()
                .userId(user.getId())
                .provider(user.getOauthInfo().getProvider())
                .profile(user.getProfile())
                .build();
    }
}
