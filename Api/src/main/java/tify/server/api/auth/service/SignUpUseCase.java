package tify.server.api.auth.service;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.auth.model.KakaoUserInfoDto;
import tify.server.api.auth.model.response.AuthResponse;
import tify.server.api.auth.model.response.OauthLoginLinkResponse;
import tify.server.api.auth.model.response.OauthTokenResponse;
import tify.server.api.auth.model.response.UserCanRegisterResponse;
import tify.server.api.auth.service.helper.AppleOauthHelper;
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

    private final AppleOauthHelper appleOauthHelper;

    private final UserDomainService userDomainService;

    public OauthLoginLinkResponse getKaKaoOauthLinkTest() {
        return new OauthLoginLinkResponse(kakaoOauthHelper.getKaKaoOauthLinkTest());
    }

    public OauthLoginLinkResponse getKaKaoOauthLink(String referer) {
        return new OauthLoginLinkResponse(kakaoOauthHelper.getKaKaoOauthLink(referer));
    }

    public OauthLoginLinkResponse getAppleOauthLink(String referer) {
        return new OauthLoginLinkResponse(appleOauthHelper.getAppleOauthLink(referer));
    }

    public AuthResponse registerUserByKakaoOICDToken(String idToken) {

        OauthInfo oauthInfo = kakaoOauthHelper.getOauthInfoByIdToken(idToken);
        User user = userDomainService.registerUser(oauthInfo);

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

    public OauthTokenResponse getCredentialFromApple(String code)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return OauthTokenResponse.from(appleOauthHelper.getOauthToken(code));
    }

    public OauthTokenResponse getCredentialFromKaKaoTest(String code) {

        return OauthTokenResponse.from(kakaoOauthHelper.getOauthTokenTest(code));
    }

    public UserCanRegisterResponse retrieveUserCanRegisterByKakao(String idToken) {
        OauthInfo oauthInfo = kakaoOauthHelper.getOauthInfoByIdToken(idToken);
        return UserCanRegisterResponse.from(userDomainService.userCanRegister(oauthInfo));
    }

    public UserCanRegisterResponse retrieveUserCanRegisterByApple(String idToken) {
        OauthInfo oauthInfo = appleOauthHelper.getOauthInfoByIdToken(idToken);
        return UserCanRegisterResponse.from(userDomainService.userCanRegister(oauthInfo));
    }

    @Transactional
    public AuthResponse registerUserByAppleOIDCToken(String idToken, String refreshToken) {
        OauthInfo oauthInfo = appleOauthHelper.getOauthInfoByIdToken(idToken);
        User user = userDomainService.registerUser(oauthInfo);
        user.updateAppleRefreshToken(refreshToken);

        return tokenGenerateHelper.execute(user);
    }
}
