package tify.server.domain.domains.user.adaptor;


import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.domain.NeighborApplication;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.dto.condition.NeighborCondition;
import tify.server.domain.domains.user.dto.model.GetNeighborApplicationDTO;
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

    public List<Neighbor> queryAllByToUserId(Long toUserId) {
        return neighborRepository.findAllByToUserId(toUserId);
    }

    public List<Neighbor> queryAllByFromUserIdAndIsView(Long fromUserId, boolean isView) {
        return neighborRepository.findAllByFromUserIdAndIsView(fromUserId, isView);
    }

    public Optional<Neighbor> queryByFromUserIdAndToUserId(Long userId, Long neighborId) {
        return neighborRepository.findByFromUserIdAndToUserId(userId, neighborId);
    }

    public List<RetrieveNeighborDTO> searchNeighbors(Long userId) {
        return neighborRepository.searchNeighbors(userId);
    }

    public Slice<RetrieveNeighborDTO> searchNeighbors(
            NeighborCondition neighborCondition, Pageable pageable) {
        return neighborRepository.searchNeighborsToPage(neighborCondition, pageable);
    }

    public boolean existsNeighbor(Long userId, Long neighborId) {
        return neighborRepository.existsByFromUserIdAndToUserId(userId, neighborId);
    }

    public void save(Neighbor neighbor) {
        neighborRepository.save(neighbor);
    }

    public void delete(Neighbor neighbor) {
        neighborRepository.delete(neighbor);
    }

    public List<RetrieveNeighborDTO> searchBirthdayNeighbors(Long userId) {
        return neighborRepository.searchBirthdayNeighbors(userId);
    }

    public void saveNeighborApplication(NeighborApplication neighborApplication) {
        neighborApplicationRepository.save(neighborApplication);
    }

    public NeighborApplication queryNeighborApplication(Long neighborApplicationId) {
        return neighborApplicationRepository
                .findById(neighborApplicationId)
                .orElseThrow(() -> NeighborApplicationNotFoundException.EXCEPTION);
    }

    public Slice<GetNeighborApplicationDTO> searchNeighborApplications(
            Pageable pageable, User toUser) {
        // 내가 아는 친구
        List<Long> currentUserNeighborIds =
                neighborRepository.findAllByFromUserId(toUser.getId()).stream()
                        .map(Neighbor::getToUserId)
                        .toList();
        return neighborApplicationRepository
                .searchAllNeighborApplicationByFromUserId(pageable, toUser.getId())
                .map(
                        dto -> {
                            List<Long> neighborIds =
                                    neighborRepository
                                            .findAllByFromUserId(dto.getUser().getId())
                                            .stream()
                                            .map(Neighbor::getToUserId)
                                            .toList();
                            List<Long> mutualNeighbors =
                                    currentUserNeighborIds.stream()
                                            .filter(neighborIds::contains)
                                            .toList();
                            return GetNeighborApplicationDTO.of(dto, mutualNeighbors.size());
                        });
    }

    public Optional<NeighborApplication> optionalQueryByFromUserIdAndToUserId(
            Long fromUserId, Long toUserId) {
        return neighborApplicationRepository.findByFromUserIdAndToUserId(fromUserId, toUserId);
    }

    public void deleteNeighborApplication(NeighborApplication neighborApplication) {
        neighborApplicationRepository.delete(neighborApplication);
    }
}
