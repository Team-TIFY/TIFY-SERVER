package tify.server.domain.domains.user.adaptor;


import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.exception.NeighborNotFoundException;
import tify.server.domain.domains.user.repository.NeighborRepository;

@Adaptor
@RequiredArgsConstructor
public class NeighborAdaptor {

    private final NeighborRepository neighborRepository;

    public Neighbor query(Long neighborId) {
        return neighborRepository
                .findById(neighborId)
                .orElseThrow(() -> NeighborNotFoundException.EXCEPTION);
    }

    public List<Neighbor> queryAllByFromUserId(Long fromUserId) {
        return neighborRepository.findAllByFromUserId(fromUserId);
    }
}
