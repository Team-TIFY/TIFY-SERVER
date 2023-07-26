package tify.server.domain.domains.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.Neighbor;

import java.util.List;

public interface NeighborRepository extends JpaRepository<Neighbor, Long> {
  List<Neighbor> findAllByFromUserId(Long fromUserId);
}
