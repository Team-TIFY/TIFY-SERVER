package tify.server.domain.domains.user.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.OauthInfo;
import tify.server.domain.domains.user.domain.UserResign;

public interface UserResignRepository extends JpaRepository<UserResign, Long> {

    Optional<UserResign> findByUserId(Long userId);

    Optional<UserResign> findByOauthInfo(OauthInfo oauthInfo);

    boolean existsByOauthInfo(OauthInfo oauthInfo);
}
