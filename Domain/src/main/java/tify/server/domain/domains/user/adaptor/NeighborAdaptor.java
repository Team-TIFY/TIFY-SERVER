package tify.server.domain.domains.user.adaptor;


import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.dto.condition.NeighborCondition;
import tify.server.domain.domains.user.dto.model.NeighborVo;
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

    public Optional<Neighbor> queryByFromUserIdAndToUserId(Long userId, Long neighborId) {
        return neighborRepository.findByFromUserIdAndToUserId(userId, neighborId);
    }

    public Slice<NeighborVo> searchNeighbors(NeighborCondition neighborCondition) {
        return neighborRepository.searchToPage(neighborCondition);
    }
}
