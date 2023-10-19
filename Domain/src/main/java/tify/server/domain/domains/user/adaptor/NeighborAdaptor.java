package tify.server.domain.domains.user.adaptor;


import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.domain.NeighborApplication;
import tify.server.domain.domains.user.dto.condition.NeighborCondition;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborApplicationDTO;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborDTO;
import tify.server.domain.domains.user.exception.NeighborApplicationNotFoundException;
import tify.server.domain.domains.user.exception.NeighborNotFoundException;
import tify.server.domain.domains.user.repository.NeighborApplicationRepository;
import tify.server.domain.domains.user.repository.NeighborRepository;

@Adaptor
@RequiredArgsConstructor
public class NeighborAdaptor {

    private final NeighborRepository neighborRepository;
    private final NeighborApplicationRepository neighborApplicationRepository;

    public Neighbor query(Long neighborId) {
        return neighborRepository
                .findById(neighborId)
                .orElseThrow(() -> NeighborNotFoundException.EXCEPTION);
    }

    public Neighbor queryByUserId(Long neighborId, Long userId) {
        return neighborRepository
                .findByIdAndFromUserId(neighborId, userId)
                .orElseThrow(() -> NeighborNotFoundException.EXCEPTION);
    }

    public List<Neighbor> queryAllByFromUserId(Long fromUserId) {
        return neighborRepository.findAllByFromUserId(fromUserId);
    }

    public Optional<Neighbor> queryByFromUserIdAndToUserId(Long userId, Long neighborId) {
        return neighborRepository.findByFromUserIdAndToUserId(userId, neighborId);
    }

    public Slice<RetrieveNeighborDTO> searchNeighbors(NeighborCondition neighborCondition) {
        return neighborRepository.searchToPage(neighborCondition);
    }

    public boolean existsNeighbor(Long userId, Long neighborId) {
        return neighborRepository.existsByFromUserIdAndToUserId(userId, neighborId);
    }

    public void save(Neighbor neighbor) {
        neighborRepository.save(neighbor);
    }

    public Slice<RetrieveNeighborDTO> searchBirthdayNeighbors(NeighborCondition neighborCondition) {
        return neighborRepository.searchBirthToPage(neighborCondition);
    }

    public NeighborApplication queryNeighborApplication(Long neighborApplicationId) {
        return neighborApplicationRepository
                .findById(neighborApplicationId)
                .orElseThrow(() -> NeighborApplicationNotFoundException.EXCEPTION);
    }

    public Slice<RetrieveNeighborApplicationDTO> searchNeighborApplications(
            Pageable pageable, Long toUserId) {
        return neighborApplicationRepository.searchAllNeighborApplicationByFromUserId(
                pageable, toUserId);
    }
}
