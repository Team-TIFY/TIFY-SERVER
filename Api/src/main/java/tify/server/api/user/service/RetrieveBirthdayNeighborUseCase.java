package tify.server.api.user.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.utils.UserUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserBlockAdaptor;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborDTO;

@UseCase
@RequiredArgsConstructor
public class RetrieveBirthdayNeighborUseCase {

    private final NeighborAdaptor neighborAdaptor;
    private final UserBlockAdaptor userBlockAdaptor;
    private final UserUtils userUtils;

    @Transactional(readOnly = true)
    public List<RetrieveNeighborDTO> execute() {
        Long currentUserId = userUtils.getUserId();
        return neighborAdaptor.searchBirthdayNeighbors(currentUserId);
    }
}
