package tify.server.api.user.service;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.common.slice.SliceResponse;
import tify.server.api.utils.UserUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.dto.condition.NeighborCondition;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborDTO;

@UseCase
@RequiredArgsConstructor
public class RetrieveNeighborListUseCase {

    private final NeighborAdaptor neighborAdaptor;
    private final UserAdaptor userAdaptor;
    private final UserUtils userUtils;

    @Transactional(readOnly = true)
    public SliceResponse<RetrieveNeighborDTO> execute(Pageable pageable) {
        Long currentUserId = userUtils.getUserId();
        List<Long> neighbors =
                new ArrayList<>(
                        neighborAdaptor.queryAllByFromUserId(currentUserId).stream()
                                .map(Neighbor::getToUserId)
                                .toList());
        NeighborCondition neighborCondition = new NeighborCondition(neighbors, pageable);
        Slice<RetrieveNeighborDTO> neighborDTOS =
                neighborAdaptor.searchNeighbors(neighborCondition);
        return SliceResponse.of(neighborDTOS);
    }
}
