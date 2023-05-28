package tify.server.api.auth.model.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AuthResponse {

    private final String accessToken;

    private final String refreshToken;

    private final Long userId;
}
