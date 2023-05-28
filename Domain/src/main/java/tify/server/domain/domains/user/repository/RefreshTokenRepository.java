package tify.server.domain.domains.user.repository;


import tify.server.domain.domains.user.domain.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, Long> {}
