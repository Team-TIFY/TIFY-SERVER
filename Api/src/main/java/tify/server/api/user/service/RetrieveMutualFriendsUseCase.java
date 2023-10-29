package tify.server.api.user.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.api.user.model.dto.vo.MutualFriendsVo;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.repository.NeighborRepository;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrieveMutualFriendsUseCase {

    private final NeighborRepository neighborRepository;

    public MutualFriendsVo execute(Long toUserId) {
        Long userId = SecurityUtils.getCurrentUserId();

        List<Long> myNeighbors =
                neighborRepository.findAllByFromUserId(userId).stream()
                        .map(Neighbor::getToUserId)
                        .toList();

        List<Long> findUserNeighbors =
                neighborRepository.findAllByFromUserId(toUserId).stream()
                        .map(Neighbor::getToUserId)
                        .toList();

        long mutualFriendsCount = myNeighbors.stream().filter(findUserNeighbors::contains).count();

        return MutualFriendsVo.of(userId, toUserId, mutualFriendsCount);
    }
}
