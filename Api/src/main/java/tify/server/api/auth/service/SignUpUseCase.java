package tify.server.api.auth.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tify.server.api.auth.model.KakaoUserInfoDto;
import tify.server.api.auth.model.request.RegisterRequest;
import tify.server.api.auth.model.response.AuthResponse;
import tify.server.api.auth.model.response.OauthLoginLinkResponse;
import tify.server.api.auth.model.response.OauthTokenResponse;
import tify.server.api.auth.model.response.UserCanRegisterResponse;
import tify.server.api.auth.service.helper.KakaoOauthHelper;
import tify.server.api.auth.service.helper.OIDCHelper;
import tify.server.api.auth.service.helper.TokenGenerateHelper;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.domain.OauthInfo;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.service.UserDomainService;

@UseCase
@RequiredArgsConstructor
@Slf4j
public class SignUpUseCase {

    private final TokenGenerateHelper tokenGenerateHelper;

    private final OIDCHelper oidcHelper;

    private final KakaoOauthHelper kakaoOauthHelper;

    private final UserDomainService userDomainService;

    public OauthLoginLinkResponse getKaKaoOauthLinkTest() {
        return new OauthLoginLinkResponse(kakaoOauthHelper.getKaKaoOauthLinkTest());
    }

    public OauthLoginLinkResponse getKaKaoOauthLink(String referer) {
        return new OauthLoginLinkResponse(kakaoOauthHelper.getKaKaoOauthLink(referer));
    }

    public AuthResponse registerUserByOICDToken(
            String idToken, RegisterRequest registerUserRequest) {

        OauthInfo oauthInfo = kakaoOauthHelper.getOauthInfoByIdToken(idToken);
        User user = userDomainService.registerUser(registerUserRequest.toProfile(), oauthInfo);

        return tokenGenerateHelper.execute(user);
    }

    public AuthResponse registerUserByKakaoCode(String code) {
        String accessToken = kakaoOauthHelper.getOauthTokenTest(code).getAccessToken();
        KakaoUserInfoDto userInfo = kakaoOauthHelper.getUserInfo(accessToken);

        User user =
                userDomainService.registerUserForTest(userInfo.toProfile(), userInfo.toOauthInfo());

        return tokenGenerateHelper.execute(user);
    }

    public OauthTokenResponse getCredentialFromKaKao(String code, String referer) {

        return OauthTokenResponse.from(kakaoOauthHelper.getOauthToken(code, referer));
    }

    public OauthTokenResponse getCredentialFromKaKaoTest(String code) {

        return OauthTokenResponse.from(kakaoOauthHelper.getOauthTokenTest(code));
    }

    public UserCanRegisterResponse retrieveUserCanRegister(String idToken) {
        OauthInfo oauthInfo = kakaoOauthHelper.getOauthInfoByIdToken(idToken);
        return UserCanRegisterResponse.from(userDomainService.userCanRegister(oauthInfo));
    }
}
