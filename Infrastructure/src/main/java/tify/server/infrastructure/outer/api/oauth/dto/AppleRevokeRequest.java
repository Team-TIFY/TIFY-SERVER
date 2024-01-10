package tify.server.infrastructure.outer.api.oauth.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AppleRevokeRequest {

    private String client_id;

    private String client_secret;

    private String token;

    private String token_type_hint;

    @Builder
    public AppleRevokeRequest(String client_id, String client_secret, String token, String type) {
        this.client_id = client_id;
        this.client_secret = client_secret;
        this.token = token;
        this.token_type_hint = type;
    }
}
