package tify.server.domain.domains.user.repository;


import org.springframework.data.repository.CrudRepository;
import tify.server.domain.domains.user.domain.RefreshTokenEntity;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, Long> {}
