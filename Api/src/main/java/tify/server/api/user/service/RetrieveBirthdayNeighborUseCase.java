package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.common.slice.SliceResponse;
import tify.server.api.utils.UserUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.dto.condition.NeighborCondition;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborDTO;

@UseCase
@RequiredArgsConstructor
public class RetrieveBirthdayNeighborUseCase {

    private final NeighborAdaptor neighborAdaptor;
    private final UserUtils userUtils;

    @Transactional(readOnly = true)
    public SliceResponse<RetrieveNeighborDTO> execute(Pageable pageable) {
        Long currentUserId = userUtils.getUserId();
        NeighborCondition neighborCondition = new NeighborCondition(currentUserId, pageable);
        Slice<RetrieveNeighborDTO> neighborDTOS =
                neighborAdaptor.searchBirthdayNeighbors(neighborCondition);
        return SliceResponse.of(neighborDTOS);
    }
}
