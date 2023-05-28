package tify.server.domain.domains.user.adaptor;


import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.user.domain.RefreshTokenEntity;
import tify.server.domain.domains.user.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;

@Adaptor
@RequiredArgsConstructor
public class RefreshTokenAdaptor {

    private final RefreshTokenRepository refreshTokenRepository;

    public void save(RefreshTokenEntity refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }
}
