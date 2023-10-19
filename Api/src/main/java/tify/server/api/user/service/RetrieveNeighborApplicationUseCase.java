package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborApplicationDTO;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrieveNeighborApplicationUseCase {

    private final NeighborAdaptor neighborAdaptor;

    public Slice<RetrieveNeighborApplicationDTO> execute(Pageable pageable) {
        Long toUserId = SecurityUtils.getCurrentUserId();
        return neighborAdaptor.searchNeighborApplications(pageable, toUserId);
    }
}
