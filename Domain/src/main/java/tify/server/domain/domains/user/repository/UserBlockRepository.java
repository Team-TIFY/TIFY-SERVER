package tify.server.domain.domains.user.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.UserBlock;

public interface UserBlockRepository extends JpaRepository<UserBlock, Long> {

    public Optional<UserBlock> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);

    public boolean existsByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
}
