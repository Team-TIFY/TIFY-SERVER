package tify.server.api.auth.service.helper;

import static tify.server.core.consts.StaticVal.BEARER;

import lombok.RequiredArgsConstructor;
import tify.server.api.auth.model.KakaoUserInfoDto;
import tify.server.core.annotation.Helper;
import tify.server.core.dto.OIDCDto;
import tify.server.core.properties.OauthProperties;
import tify.server.domain.domains.user.domain.OauthInfo;
import tify.server.domain.domains.user.domain.OauthProvider;
import tify.server.infrastructure.outer.api.oauth.client.KakaoInfoClient;
import tify.server.infrastructure.outer.api.oauth.client.KakaoOauthClient;
import tify.server.infrastructure.outer.api.oauth.dto.KakaoInfoResponse;
import tify.server.infrastructure.outer.api.oauth.dto.KakaoTokenResponse;
import tify.server.infrastructure.outer.api.oauth.dto.OIDCResponse;

@RequiredArgsConstructor
@Helper
public class KakaoOauthHelper {

    private final OauthProperties oauthProperties;

    private final KakaoInfoClient kakaoInfoClient;
    private final KakaoOauthClient kakaoOauthClient;

    private final OIDCHelper oidcHelper;

    private static final String KAKAO_OAUTH_QUERY_STRING =
            "/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code";

    public String getKaKaoOauthLinkTest() {
        return oauthProperties.getKakaoBaseUrl()
                + String.format(
                        KAKAO_OAUTH_QUERY_STRING,
                        oauthProperties.getKakaoClientId(),
                        oauthProperties.getKakaoRedirectUrl());
    }

    public String getKaKaoOauthLink(String referer) {
        System.out.println("referer = " + referer);
        return oauthProperties.getKakaoBaseUrl()
                + String.format(
                        KAKAO_OAUTH_QUERY_STRING,
                        oauthProperties.getKakaoClientId(),
                        referer + "kakao/callback");
    }

    public KakaoTokenResponse getOauthToken(String code, String referer) {

        return kakaoOauthClient.kakaoAuth(
                oauthProperties.getKakaoClientId(),
                referer + "kakao/callback",
                code,
                oauthProperties.getKakaoClientSecret());
    }

    public KakaoTokenResponse getOauthTokenTest(String code) {

        return kakaoOauthClient.kakaoAuth(
                oauthProperties.getKakaoClientId(),
                oauthProperties.getKakaoRedirectUrl(),
                code,
                oauthProperties.getKakaoClientSecret());
    }

    public KakaoUserInfoDto getUserInfo(String oauthAccessToken) {
        System.out.println("oauthAccessToken = " + oauthAccessToken);
        KakaoInfoResponse response = kakaoInfoClient.kakaoUserInfo(BEARER + oauthAccessToken);

        return KakaoUserInfoDto.builder()
                .oauthProvider(OauthProvider.KAKAO)
                .userName(response.getName())
                .profileImage(response.getProfileUrl())
                .email(response.getEmail())
                .oauthId(response.getId())
                .build();
    }

    public OIDCDto getOIDCDecodePayload(String token) {
        OIDCResponse oidcResponse = kakaoOauthClient.getKakaoOIDCOpenKeys();
        System.out.println(
                "oidcPublicKeysResponse.getOidcPublicKeyDtos().toString() = "
                        + oidcResponse.getKeys().toString());
        return oidcHelper.getPayloadFromIdToken(
                token,
                oauthProperties.getKakaoBaseUrl(),
                oauthProperties.getKakaoAppId(),
                oidcResponse);
    }

    public OauthInfo getOauthInfoByIdToken(String idToken) {
        OIDCDto oidcDecodePayload = getOIDCDecodePayload(idToken);
        return OauthInfo.builder()
                .provider(OauthProvider.KAKAO)
                .oid(oidcDecodePayload.getSub())
                .build();
    }

    //    public void unlink(String oid) {
    //        String kakaoAdminKey = oauthProperties.getKakaoAdminKey();
    //        UnlinkKaKaoTarget unlinkKaKaoTarget = UnlinkKaKaoTarget.from(oid);
    //        String header = "KakaoAK " + kakaoAdminKey;
    //        kakaoInfoClient.unlinkUser(header, unlinkKaKaoTarget);
    //    }
}
