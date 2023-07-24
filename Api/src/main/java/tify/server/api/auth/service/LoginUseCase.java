package tify.server.api.auth.service;


import lombok.RequiredArgsConstructor;
import tify.server.api.auth.model.response.AuthResponse;
import tify.server.api.auth.service.helper.KakaoOauthHelper;
import tify.server.api.auth.service.helper.TokenGenerateHelper;
import tify.server.core.annotation.UseCase;
import tify.server.core.jwt.JwtTokenProvider;
import tify.server.domain.domains.user.adaptor.RefreshTokenAdaptor;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.service.UserDomainService;

@UseCase
@RequiredArgsConstructor
public class LoginUseCase {

    private final UserDomainService userDomainService;
    private final KakaoOauthHelper kakaoOauthHelper;
    private final TokenGenerateHelper tokenGenerateHelper;
    private final RefreshTokenAdaptor refreshTokenAdaptor;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserAdaptor userAdaptor;

    public AuthResponse execute(String idToken) {
        User user = userDomainService.loginUser(kakaoOauthHelper.getOauthInfoByIdToken(idToken));
        return tokenGenerateHelper.execute(user);
    }

    public AuthResponse reissue(String refreshToken) {

        Long refreshUserId =
                jwtTokenProvider.parseRefreshToken(
                        refreshTokenAdaptor.query(refreshToken).getRefreshToken());

        User user = userAdaptor.query(refreshUserId);
        // 리프레쉬 시에도 last로그인 정보 업데이트
        userDomainService.loginUser(user.getOauthInfo());
        return tokenGenerateHelper.execute(user);
    }
}
