package tify.server.api.auth.service.helper;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tify.server.api.auth.model.response.OauthRefreshResponse;
import tify.server.core.annotation.Helper;
import tify.server.core.dto.OIDCDto;
import tify.server.core.jwt.JwtOIDCProvider;
import tify.server.core.jwt.JwtTokenProvider;
import tify.server.core.properties.OauthProperties;
import tify.server.domain.domains.user.domain.OauthInfo;
import tify.server.domain.domains.user.domain.OauthProvider;
import tify.server.infrastructure.outer.api.oauth.client.AppleKeyClient;
import tify.server.infrastructure.outer.api.oauth.client.AppleOauthClient;
import tify.server.infrastructure.outer.api.oauth.dto.AppleOIDCPublicKeyDto;
import tify.server.infrastructure.outer.api.oauth.dto.ApplePublicKeyResponse;
import tify.server.infrastructure.outer.api.oauth.dto.AppleRefreshRequest;
import tify.server.infrastructure.outer.api.oauth.dto.AppleTokenRequest;
import tify.server.infrastructure.outer.api.oauth.dto.AppleTokenResponse;

@Slf4j
@Helper
@RequiredArgsConstructor
public class AppleOauthHelper {

    private final OauthProperties oauthProperties;
    private final AppleOauthClient appleOauthClient;
    private final AppleKeyClient appleKeyClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtOIDCProvider jwtOIDCProvider;
    private final OIDCHelper oidcHelper;

    public String getAppleOauthLink(String referer) {
        log.info("referer : {}", referer);
        return oauthProperties.getAppleBaseUrl()
                + "/auth/authorize"
                + "?client_id="
                + oauthProperties.getAppleClientUrl()
                + "&redirect_uri="
                + oauthProperties.getAppleRedirectUrl()
                + "&response_type=code%20id_token&scope=name%20email&response_mode=form_post";
    }

    public AppleTokenResponse getOauthToken(String code)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return appleOauthClient.appleAuth(
                AppleTokenRequest.of(
                        code,
                        oauthProperties.getAppleClientUrl(),
                        "authorization_code",
                        jwtTokenProvider.buildAppleClientSecret()));
    }

    public OIDCDto getOIDCDecodePayload(String token) {
        ApplePublicKeyResponse response = appleKeyClient.getAppleAuthPublicKey();

        String kid =
                oidcHelper.getKidFromUnsignedIdToken(
                        token,
                        oauthProperties.getAppleBaseUrl(),
                        oauthProperties.getAppleClientUrl());

        String alg =
                oidcHelper.getAlgfromUnsignedIdToken(
                        token,
                        oauthProperties.getAppleBaseUrl(),
                        oauthProperties.getAppleClientUrl());

        AppleOIDCPublicKeyDto appleOIDCPublicKeyDto =
                response.getKeys().stream()
                        .filter(o -> o.getKid().equals(kid) && o.getAlg().equals(alg))
                        .findFirst()
                        .orElseThrow();

        return jwtOIDCProvider.getOIDCTokenBody(
                token, appleOIDCPublicKeyDto.getN(), appleOIDCPublicKeyDto.getE());
    }

    public OauthInfo getOauthInfoByIdToken(String idToken) {
        OIDCDto oidcDecodePayload = getOIDCDecodePayload(idToken);
        return OauthInfo.builder()
                .provider(OauthProvider.APPLE)
                .oid(oidcDecodePayload.getSub())
                .build();
    }

    public OauthRefreshResponse validateRefreshToken(String refreshToken)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return OauthRefreshResponse.from(
                appleOauthClient.appleAuth(
                        AppleRefreshRequest.of(
                                refreshToken,
                                oauthProperties.getAppleClientUrl(),
                                "refresh_token",
                                jwtTokenProvider.buildAppleClientSecret())));
    }
}
