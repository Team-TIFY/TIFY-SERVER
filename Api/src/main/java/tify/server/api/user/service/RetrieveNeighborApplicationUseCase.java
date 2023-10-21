package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.dto.model.GetNeighborApplicationDTO;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrieveNeighborApplicationUseCase {

    private final NeighborAdaptor neighborAdaptor;
    private final UserAdaptor userAdaptor;

    public Slice<GetNeighborApplicationDTO> execute(Pageable pageable) {
        Long toUserId = SecurityUtils.getCurrentUserId();
        User user = userAdaptor.query(toUserId);
        return neighborAdaptor.searchNeighborApplications(pageable, user);
    }
}
