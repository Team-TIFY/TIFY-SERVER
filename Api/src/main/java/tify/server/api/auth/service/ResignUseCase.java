package tify.server.api.auth.service;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.core.annotation.UseCase;
import tify.server.core.jwt.JwtTokenProvider;
import tify.server.core.properties.OauthProperties;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.adaptor.UserResignAdaptor;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserResign;
import tify.server.domain.domains.user.validator.UserValidator;
import tify.server.infrastructure.outer.api.oauth.client.AppleRevokeClient;
import tify.server.infrastructure.outer.api.oauth.dto.AppleRevokeRequest;

@UseCase
@RequiredArgsConstructor
public class ResignUseCase {

    private final UserAdaptor userAdaptor;
    private final UserValidator userValidator;
    private final UserResignAdaptor userResignAdaptor;
    private final OauthProperties oauthProperties;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppleRevokeClient appleRevokeClient;

    @Transactional
    public void execute(Long userId) {
        User user = userAdaptor.query(userId);
        userValidator.isNewResign(user.getOauthInfo());
        userResignAdaptor.save(
                UserResign.builder().userId(userId).oauthInfo(user.getOauthInfo()).build());
    }

    @Transactional(readOnly = true)
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

    @Transactional
    public void delete(Long userId) {
        UserResign userResign = userResignAdaptor.queryByUserId(userId);
        userResignAdaptor.delete(userResign);
    }
}
