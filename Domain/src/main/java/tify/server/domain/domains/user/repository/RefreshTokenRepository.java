package tify.server.domain.domains.user.repository;


import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import tify.server.domain.domains.user.domain.RefreshTokenEntity;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByRefreshToken(String refreshToken);
}
