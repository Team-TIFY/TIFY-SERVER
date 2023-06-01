package tify.server.api.auth.model;


import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.OauthInfo;
import tify.server.domain.domains.user.domain.OauthProvider;
import tify.server.domain.domains.user.domain.Profile;

@Getter
@Builder
public class KakaoUserInfoDto {

    private final String oauthId;

    private final String email;
    private final String phoneNum;
    private final String profileImage;
    private final String userName;
    // oauth 제공자
    private final OauthProvider oauthProvider;

    public Profile toProfile() {
        return Profile.builder()
                .thumbNail(this.profileImage)
                .userName(userName)
                .email(email)
                .build();
    }

    public OauthInfo toOauthInfo() {
        return OauthInfo.builder().oid(oauthId).provider(oauthProvider).build();
    }
}
