package tify.server.domain.domains.user.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserOpinion;

public interface UserOpinionRepository extends JpaRepository<UserOpinion, Long> {

    List<UserOpinion> findAllByUser(User user);
}
