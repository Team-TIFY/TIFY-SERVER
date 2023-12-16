package tify.server.api.auth.model.response;


import lombok.Builder;
import lombok.Getter;
import tify.server.infrastructure.outer.api.oauth.dto.AppleRefreshResponse;

@Getter
@Builder
public class OauthRefreshResponse {

    private String accessToken;

    private String tokenType;

    private Long expiresIn;

    public static OauthRefreshResponse from(AppleRefreshResponse response) {
        return OauthRefreshResponse.builder()
                .accessToken(response.getAccessToken())
                .tokenType(response.getTokenType())
                .expiresIn(response.getExpiresIn())
                .build();
    }
}
