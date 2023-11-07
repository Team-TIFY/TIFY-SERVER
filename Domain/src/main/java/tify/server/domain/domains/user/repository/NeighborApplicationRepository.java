package tify.server.domain.domains.user.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.NeighborApplication;

public interface NeighborApplicationRepository
        extends JpaRepository<NeighborApplication, Long>, NeighborApplicationCustomRepository {
    Optional<NeighborApplication> findByFromUserId(Long fromUserId);

    Optional<NeighborApplication> findByToUserId(Long toUserId);

    Optional<NeighborApplication> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);
}
