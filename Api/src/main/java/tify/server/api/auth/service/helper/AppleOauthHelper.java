package tify.server.api.auth.service.helper;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tify.server.core.annotation.Helper;
import tify.server.core.properties.OauthProperties;
import tify.server.infrastructure.outer.api.oauth.client.AppleOauthClient;
import tify.server.infrastructure.outer.api.oauth.dto.AppleTokenRequest;
import tify.server.infrastructure.outer.api.oauth.dto.AppleTokenResponse;

@Slf4j
@Helper
@RequiredArgsConstructor
public class AppleOauthHelper {

    private final OauthProperties oauthProperties;
    private final AppleOauthClient appleOauthClient;

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

    public AppleTokenResponse getOauthToken(String code, String referer) {
        return appleOauthClient.appleAuth(AppleTokenRequest.of(code, oauthProperties.getAppleClientUrl(), null, "authorization_code", null));
    }
}
