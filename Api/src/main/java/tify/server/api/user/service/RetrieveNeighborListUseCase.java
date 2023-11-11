package tify.server.api.user.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.utils.UserUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.adaptor.UserBlockAdaptor;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.domain.UserBlock;
import tify.server.domain.domains.user.dto.condition.NeighborCondition;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborDTO;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class RetrieveNeighborListUseCase {

    private final NeighborAdaptor neighborAdaptor;
    private final UserAdaptor userAdaptor;
    private final UserBlockAdaptor userBlockAdaptor;
    private final UserUtils userUtils;

    @Transactional(readOnly = true)
    public List<RetrieveNeighborDTO> execute() {
        Long currentUserId = userUtils.getUserId();
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
        return neighborAdaptor.searchNeighbors(neighborCondition);
    }

    @Transactional(readOnly = true)
    public List<RetrieveNeighborDTO> executeToIsNew() {
        Long currentUserId = userUtils.getUserId();
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
        return neighborAdaptor.searchNeighbors(neighborCondition).stream()
                .filter(RetrieveNeighborDTO::isNew)
                .toList();
    }
}
