package tify.server.domain.domains.user.adaptor;


import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.core.exception.ExpiredRefreshTokenException;
import tify.server.domain.domains.user.domain.RefreshTokenEntity;
import tify.server.domain.domains.user.repository.RefreshTokenRepository;

@Adaptor
@RequiredArgsConstructor
public class RefreshTokenAdaptor {

    private final RefreshTokenRepository refreshTokenRepository;

    public void save(RefreshTokenEntity refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

    public RefreshTokenEntity query(String refreshToken) {
        return refreshTokenRepository
                .findByRefreshToken(refreshToken)
                .orElseThrow(() -> ExpiredRefreshTokenException.EXCEPTION);
    }

    public void delete(Long userId) {
        refreshTokenRepository.deleteById(userId);
    }
}
