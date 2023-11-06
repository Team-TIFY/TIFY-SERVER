package tify.server.api.user.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.api.user.model.dto.vo.UserSearchInfoVo;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.dto.condition.UserCondition;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrieveUserListUseCase {

    private final UserAdaptor userAdaptor;
    private final NeighborAdaptor neighborAdaptor;

    public Slice<UserSearchInfoVo> execute(Pageable pageable, UserCondition condition) {
        Long userId = SecurityUtils.getCurrentUserId();

        List<Long> myNeighbors =
                neighborAdaptor.queryAllByFromUserId(userId).stream()
                        .map(Neighbor::getToUserId)
                        .toList();

        return userAdaptor
                .searchUsers(pageable, condition, userId)
                .map(
                        user -> {
                            List<Long> findUserNeighbors =
                                    neighborAdaptor.queryAllByFromUserId(user.getId()).stream()
                                            .map(Neighbor::getToUserId)
                                            .toList();
                            int mutualFriends =
                                    (int)
                                            myNeighbors.stream()
                                                    .filter(findUserNeighbors::contains)
                                                    .count();
                            boolean isFriend = neighborAdaptor.existsNeighbor(userId, user.getId());
                            return UserSearchInfoVo.of(user, mutualFriends, isFriend);
                        });
    }
}
