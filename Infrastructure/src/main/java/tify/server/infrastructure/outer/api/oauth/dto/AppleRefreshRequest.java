package tify.server.infrastructure.outer.api.oauth.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AppleRefreshRequest {

    private String refresh_token;
    private String client_id;
    private String grant_type;
    private String client_secret;

    public static AppleRefreshRequest of(
            String refresh_token, String client_id, String grant_type, String client_secret) {
        return AppleRefreshRequest.builder()
                .refresh_token(refresh_token)
                .client_id(client_id)
                .grant_type(grant_type)
                .client_secret(client_secret)
                .build();
    }
}
