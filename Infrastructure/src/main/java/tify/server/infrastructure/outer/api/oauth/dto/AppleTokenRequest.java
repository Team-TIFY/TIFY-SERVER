package tify.server.infrastructure.outer.api.oauth.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppleTokenRequest {

    private String code;
    private String client_id;
    private String grant_type;
    private String client_secret;

    public static AppleTokenRequest of(
            String code, String clientId, String grantType, String clientSecret) {
        return AppleTokenRequest.builder()
                .code(code)
                .client_id(clientId)
                .grant_type(grantType)
                .client_secret(clientSecret)
                .build();
    }
}
