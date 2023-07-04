package tify.server.api.auth.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tify.server.api.auth.model.request.RegisterRequest;
import tify.server.api.auth.model.response.AuthResponse;
import tify.server.api.auth.model.response.OauthLoginLinkResponse;
import tify.server.api.auth.model.response.OauthTokenResponse;
import tify.server.api.auth.service.LoginUseCase;
import tify.server.api.auth.service.SignUpUseCase;

@RestController
@Slf4j
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
@Tag(name = "1-1. [인증]")
public class AuthController {

    private final SignUpUseCase signUpUseCase;
    private final LoginUseCase loginUseCase;

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
    public AuthResponse registerUser(
            @RequestParam("id_token") String token,
            @Valid @RequestBody RegisterRequest registerRequest) {
        return signUpUseCase.registerUserByOICDToken(token, registerRequest);
    }

    @Deprecated
    @Operation(summary = "개발용 회원가입 및 로그인")
    @Tag(name = "1-2. [카카오]")
    @GetMapping("/oauth/kakao/develop")
    public AuthResponse registerUserForTest(@RequestParam String code) {
        return signUpUseCase.registerUserByKakaoCode(code);
    }

    @Operation(summary = "id token 이용해서 로그인")
    @PostMapping("/oauth/kakao/login")
    public AuthResponse loginUser(@RequestParam String idToken) {
        return loginUseCase.execute(idToken);
    }
}
