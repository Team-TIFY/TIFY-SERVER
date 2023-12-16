package tify.server.api.auth.model.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRefreshTokenResponse {

    private String appleRefreshToken;
}
