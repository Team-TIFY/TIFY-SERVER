package tify.server.api.auth.service.helper;


import tify.server.api.auth.model.response.AuthResponse;
import tify.server.core.annotation.Helper;
import tify.server.core.jwt.JwtTokenProvider;
import tify.server.domain.domains.user.adaptor.RefreshTokenAdaptor;
import tify.server.domain.domains.user.domain.RefreshTokenEntity;
import tify.server.domain.domains.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Helper
@RequiredArgsConstructor
public class TokenGenerateHelper {

    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenAdaptor refreshTokenAdaptor;

    @Transactional
    public AuthResponse execute(User user) {
        String newAccessToken =
                jwtTokenProvider.generateAccessToken(user.getId(), user.getAccountRole().getRole());
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

        RefreshTokenEntity newRefreshTokenEntity =
                RefreshTokenEntity.builder()
                        .refreshToken(newRefreshToken)
                        .id(user.getId())
                        .ttl(jwtTokenProvider.getRefreshTokenTTlSecond())
                        .build();
        refreshTokenAdaptor.save(newRefreshTokenEntity);

        return AuthResponse.builder()
                .userId(user.getId())
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
