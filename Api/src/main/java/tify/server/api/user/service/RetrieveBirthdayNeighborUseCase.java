package tify.server.api.user.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.utils.UserUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserBlockAdaptor;
import tify.server.domain.domains.user.domain.UserBlock;
import tify.server.domain.domains.user.dto.condition.NeighborCondition;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborDTO;

@UseCase
@RequiredArgsConstructor
public class RetrieveBirthdayNeighborUseCase {

    private final NeighborAdaptor neighborAdaptor;
    private final UserBlockAdaptor userBlockAdaptor;
    private final UserUtils userUtils;

    @Transactional(readOnly = true)
    public List<RetrieveNeighborDTO> execute(Pageable pageable) {
        Long currentUserId = userUtils.getUserId();
        List<Long> blockedUserList =
                userBlockAdaptor.queryAllByFromUserId(currentUserId).stream()
                        .map(UserBlock::getToUserId)
                        .toList();
        NeighborCondition neighborCondition =
                new NeighborCondition(currentUserId, blockedUserList, pageable);
        return neighborAdaptor.searchBirthdayNeighbors(neighborCondition);
    }
}
