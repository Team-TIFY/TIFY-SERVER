package tify.server.domain.domains.user.repository;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.UserBlock;

public interface UserBlockRepository extends JpaRepository<UserBlock, Long> {

    Optional<UserBlock> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);

    boolean existsByFromUserIdAndToUserId(Long fromUserId, Long toUserId);

    List<UserBlock> findAllByFromUserId(Long fromUserId);

    void deleteAllByFromUserIdOrToUserId(Long fromUserId, Long toUserId);
}
