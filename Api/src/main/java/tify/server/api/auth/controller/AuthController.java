package tify.server.api.auth.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tify.server.api.auth.model.response.AuthResponse;
import tify.server.api.auth.model.response.OauthLoginLinkResponse;
import tify.server.api.auth.model.response.OauthRefreshResponse;
import tify.server.api.auth.model.response.OauthTokenResponse;
import tify.server.api.auth.model.response.UserCanRegisterResponse;
import tify.server.api.auth.model.response.UserRefreshTokenResponse;
import tify.server.api.auth.service.LoginUseCase;
import tify.server.api.auth.service.LogoutUseCase;
import tify.server.api.auth.service.ResignUseCase;
import tify.server.api.auth.service.SignUpUseCase;

@RestController
@Slf4j
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
@Tag(name = "1. [인증]")
public class AuthController {

    private final SignUpUseCase signUpUseCase;
    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;
    private final ResignUseCase resignUseCase;

    @Deprecated
    @Operation(summary = "kakao oauth 링크발급 (백엔드용)", description = "kakao 링크를 받아볼수 있습니다.")
    @GetMapping("/oauth/kakao/link/dev")
    public OauthLoginLinkResponse getKakaoLoginLinkForDev() {
        return signUpUseCase.getKaKaoOauthLinkTest();
    }

    @Operation(summary = "kakao oauth 링크발급 (프론트용)", description = "kakao 링크를 받아볼수 있습니다.")
    @GetMapping("/oauth/kakao/link")
    public OauthLoginLinkResponse getKakaoLoginLink(
            @RequestHeader(required = false) String referer,
            @RequestHeader(required = false) String host) {

        // dev, production 환경에서
        if (referer.contains(host)) {
            log.info("/oauth/kakao" + host);
            String format = String.format("https://%s/", host);
            return signUpUseCase.getKaKaoOauthLink(format);
        }
        return signUpUseCase.getKaKaoOauthLink(referer);
    }

    @Operation(summary = "kakao code를 통해 token 발급")
    @GetMapping("/oauth/kakao")
    public OauthTokenResponse getKakaoCredentialInfo(
            @RequestParam String code,
            @RequestHeader(required = false) String referer,
            @RequestHeader(required = false) String host) {

        // dev, production 환경에서
        if (referer.contains(host)) {
            log.info("/oauth/kakao" + host);
            String format = String.format("https://%s/", host);
            return signUpUseCase.getCredentialFromKaKao(code, format);
        }
        return signUpUseCase.getCredentialFromKaKao(code, referer);
    }

    @Operation(summary = "발급받은 idToken을 통해 회원가입")
    @PostMapping("/oauth/kakao/register")
    public AuthResponse registerUserByKakao(@RequestParam("id_token") String token) {
        return signUpUseCase.registerUserByKakaoOICDToken(token);
    }

    @Deprecated
    @Operation(summary = "개발용 회원가입 및 로그인")
    @GetMapping("/oauth/kakao/develop")
    public AuthResponse registerUserForTest(@RequestParam String code) {
        return signUpUseCase.registerUserByKakaoCode(code);
    }

    @Operation(summary = "id token 이용해서 로그인")
    @PostMapping("/oauth/kakao/login")
    public AuthResponse loginUserByKakao(@RequestParam String idToken) {
        return loginUseCase.executeByKakao(idToken);
    }

    @Operation(summary = "유저가 카카오 회원가입 되어있는지 여부 조회")
    @GetMapping("/oauth/register/valid/kakao")
    public UserCanRegisterResponse getUserCanRegisterByKakao(@RequestParam String idToken) {
        return signUpUseCase.retrieveUserCanRegisterByKakao(idToken);
    }

    @Operation(summary = "리프레시 토큰으로 accessToken 재발급")
    @GetMapping("/token/refresh")
    public AuthResponse reissue(@RequestHeader(value = "refresh-token") String refreshToken) {
        return loginUseCase.reissue(refreshToken);
    }

    @SecurityRequirement(name = "access-token")
    @Operation(summary = "로그아웃")
    @PostMapping("/logout")
    public void logout(HttpServletRequest req) {
        logoutUseCase.execute(req.getHeader("Authorization"));
    }

    @Operation(summary = "apple oauth 링크발급", description = "apple 링크를 받아볼수 있습니다.")
    @GetMapping("/oauth/apple/link")
    public OauthLoginLinkResponse getAppleLoginLink(
            @RequestHeader(required = false) String referer,
            @RequestHeader(required = false) String host) {

        // dev, production 환경에서
        if (referer.contains(host)) {
            log.info("/oauth/apple/" + host);
            String format = String.format("https://%s/", host);
            return signUpUseCase.getAppleOauthLink(format);
        }
        return signUpUseCase.getAppleOauthLink(referer);
    }

    @Operation(summary = "apple code를 통해 토큰 발급")
    @GetMapping("/oauth/apple")
    public OauthTokenResponse getAppleCredentialInfo(
            @RequestParam String code,
            @RequestHeader(required = false) String referer,
            @RequestHeader(required = false) String host)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        return signUpUseCase.getCredentialFromApple(code);
    }

    @Operation(summary = "apple에서 발급한 idToken, refreshToken 이용하여 회원가입")
    @PostMapping("/oauth/apple/register")
    public AuthResponse registerUserByApple(
            @RequestParam("id_token") String idToken,
            @RequestParam("refresh_token") String refreshToken,
            @RequestHeader(required = false) String referer,
            @RequestHeader(required = false) String host) {
        return signUpUseCase.registerUserByAppleOIDCToken(idToken, refreshToken);
    }

    @Operation(summary = "apple id token으로 로그인")
    @PostMapping("oauth/apple/login")
    public AuthResponse loginUserByApple(@RequestParam String idToken) {
        return loginUseCase.executeByApple(idToken);
    }

    @Operation(summary = "유저가 애플 회원가입 되어있는지 여부 조회")
    @GetMapping("/oauth/register/valid/apple")
    public UserCanRegisterResponse getUserCanRegisterByApple(@RequestParam String idToken) {
        return signUpUseCase.retrieveUserCanRegisterByApple(idToken);
    }

    @Operation(summary = "유저의 apple refresh token 조회")
    @GetMapping("/oauth/apple/refresh")
    public UserRefreshTokenResponse getAppleRefreshToken(@RequestParam Long userId) {
        return loginUseCase.getAppleRefreshToken(userId);
    }

    @Operation(summary = "기존 refresh token으로 access token 얻어 세션 유지")
    @GetMapping("/oauth/apple/valid")
    public OauthRefreshResponse validateRefreshToken(@RequestParam String refreshToken)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        return loginUseCase.getCredentialFromApple(refreshToken);
    }

    @Operation(summary = "애플 로그인 연동 해제")
    @GetMapping("oauth/apple/revoke")
    public void revokeAppleLogin(@RequestParam Long userId)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        resignUseCase.revokeAppleToken(userId);
    }
}
