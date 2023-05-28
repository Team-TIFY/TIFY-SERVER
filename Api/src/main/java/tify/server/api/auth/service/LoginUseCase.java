package tify.server.api.auth.service;

import tify.server.api.auth.model.response.AuthResponse;
import tify.server.api.auth.service.helper.KakaoOauthHelper;
import tify.server.api.auth.service.helper.TokenGenerateHelper;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.service.UserDomainService;
import lombok.RequiredArgsConstructor;

@UseCase
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserDomainService userDomainService;
    private final KakaoOauthHelper kakaoOauthHelper;
    private final TokenGenerateHelper tokenGenerateHelper;

    public AuthResponse execute(String idToken) {
        User user = userDomainService.loginUser(kakaoOauthHelper.getOauthInfoByIdToken(idToken));
        System.out.println("user = " + user.getId());
        return tokenGenerateHelper.execute(user);
    }
}
