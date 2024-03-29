package tify.server.domain.domains.user.repository;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.Neighbor;

public interface NeighborRepository
        extends JpaRepository<Neighbor, Long>, NeighborCustomRepository {
    List<Neighbor> findAllByFromUserId(Long fromUserId);

    List<Neighbor> findAllByToUserId(Long toUserId);

    Optional<Neighbor> findByFromUserIdAndToUserId(Long userId, Long neighborId);

    Optional<Neighbor> findByIdAndFromUserId(Long id, Long fromUserId);

    boolean existsByFromUserIdAndToUserId(Long userId, Long neighborId);

    List<Neighbor> findAllByFromUserIdAndIsView(Long fromUserId, boolean isView);

    List<Neighbor> findAllByFromUserIdOrderByOrder(Long fromUserId);

    void deleteAllByFromUserIdOrToUserId(Long fromUserId, Long toUserId);
}
