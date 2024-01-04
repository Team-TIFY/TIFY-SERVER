package tify.server.api.auth.service;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.UseCase;
import tify.server.core.jwt.JwtTokenProvider;
import tify.server.core.properties.OauthProperties;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.User;
import tify.server.infrastructure.outer.api.oauth.client.AppleRevokeClient;
import tify.server.infrastructure.outer.api.oauth.dto.AppleRevokeRequest;

@UseCase
@RequiredArgsConstructor
public class ResignUseCase {

    private final UserAdaptor userAdaptor;
    private final OauthProperties oauthProperties;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppleRevokeClient appleRevokeClient;

    public void revokeAppleToken(Long userId)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        User user = userAdaptor.query(userId);
        String refreshToken = user.getAppleRefreshToken();
        AppleRevokeRequest request =
                AppleRevokeRequest.builder()
                        .client_id(oauthProperties.getAppleClientUrl())
                        .client_secret(jwtTokenProvider.buildAppleClientSecret())
                        .token(refreshToken)
                        .type("refresh_token")
                        .build();
        appleRevokeClient.appleRevoke(request);
    }
}
