package tify.server.domain.domains.user.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.Neighbor;

public interface NeighborRepository extends JpaRepository<Neighbor, Long> {
    List<Neighbor> findAllByFromUserId(Long fromUserId);
}
