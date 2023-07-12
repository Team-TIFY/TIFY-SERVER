package tify.server.domain.domains.user.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.OauthInfo;
import tify.server.domain.domains.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long>, UserCustomRepository {

    Optional<User> findByOauthInfo(OauthInfo oauthInfo);
}
