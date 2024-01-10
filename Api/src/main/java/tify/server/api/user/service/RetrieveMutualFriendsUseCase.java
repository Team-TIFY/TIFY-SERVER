package tify.server.api.user.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.api.user.model.dto.vo.MutualFriendsVo;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserResignAdaptor;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.domain.UserResign;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrieveMutualFriendsUseCase {

    private final NeighborAdaptor neighborAdaptor;
    private final UserResignAdaptor userResignAdaptor;

    public MutualFriendsVo execute(Long toUserId) {
        Long userId = SecurityUtils.getCurrentUserId();

        List<Long> resignedUsers =
                userResignAdaptor.queryAll().stream().map(UserResign::getUserId).toList();

        List<Long> myNeighbors =
                neighborAdaptor.queryAllByFromUserId(userId).stream()
                        .map(Neighbor::getToUserId)
                        .filter(id -> !resignedUsers.contains(id))
                        .toList();

        List<Long> findUserNeighbors =
                neighborAdaptor.queryAllByFromUserId(toUserId).stream()
                        .map(Neighbor::getToUserId)
                        .filter(id -> !resignedUsers.contains(id))
                        .toList();

        int mutualFriendsCount =
                (int) myNeighbors.stream().filter(findUserNeighbors::contains).count();

        return MutualFriendsVo.of(userId, toUserId, mutualFriendsCount);
    }
}
