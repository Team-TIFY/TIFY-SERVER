package tify.server.domain.domains.user.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserFavor;

public interface UserFavorRepository
        extends JpaRepository<UserFavor, Long>, UserFavorCustomRepository {

    List<UserFavor> findAllByUser(User user);
}
