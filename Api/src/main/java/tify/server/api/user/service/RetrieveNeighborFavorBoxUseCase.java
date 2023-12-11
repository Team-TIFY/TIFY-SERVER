package tify.server.api.user.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.adaptor.UserBlockAdaptor;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.domain.UserBlock;
import tify.server.domain.domains.user.dto.condition.NeighborCondition;
import tify.server.domain.domains.user.dto.model.RetrieveUserFavorBoxDTO;

@UseCase
@RequiredArgsConstructor
public class RetrieveNeighborFavorBoxUseCase {

    private final UserAdaptor userAdaptor;
    private final UserBlockAdaptor userBlockAdaptor;
    private final NeighborAdaptor neighborAdaptor;

    @Transactional(readOnly = true)
    public List<RetrieveUserFavorBoxDTO> execute() {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        List<Long> blockedUserList =
                userBlockAdaptor.queryAllByFromUserId(currentUserId).stream()
                        .map(UserBlock::getToUserId)
                        .toList();
        List<Long> friendIdList =
                neighborAdaptor.queryAllByToUserId(currentUserId).stream()
                        .map(Neighbor::getFromUserId)
                        .toList();
        NeighborCondition neighborCondition =
                new NeighborCondition(currentUserId, blockedUserList, friendIdList);

        return userAdaptor.queryUserFavorBox(currentUserId, neighborCondition).stream()
                .map(RetrieveUserFavorBoxDTO::from)
                .toList();
    }
}
