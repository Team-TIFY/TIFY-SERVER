package tify.server.api.auth.model.response;


import lombok.Builder;
import lombok.Getter;
import tify.server.infrastructure.outer.api.oauth.dto.KakaoTokenResponse;

@Getter
@Builder
public class OauthTokenResponse {

    private String accessToken;
    private String refreshToken;
    private String idToken;

    public static OauthTokenResponse from(KakaoTokenResponse kakaoTokenResponse) {
        return OauthTokenResponse.builder()
                .accessToken(kakaoTokenResponse.getAccessToken())
                .refreshToken(kakaoTokenResponse.getRefreshToken())
                .idToken(kakaoTokenResponse.getIdToken())
                .build();
    }
}
