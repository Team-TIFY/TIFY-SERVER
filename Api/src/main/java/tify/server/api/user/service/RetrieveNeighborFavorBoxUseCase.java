package tify.server.api.user.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.api.user.model.dto.vo.RetrieveUserFavorBoxVo;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.adaptor.UserBlockAdaptor;

@UseCase
@RequiredArgsConstructor
public class RetrieveNeighborFavorBoxUseCase {

    private final UserAdaptor userAdaptor;
    private final UserBlockAdaptor userBlockAdaptor;
    private final NeighborAdaptor neighborAdaptor;

    @Transactional(readOnly = true)
    public List<RetrieveUserFavorBoxVo> execute() {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        return userAdaptor.queryUserFavorBox(currentUserId).stream()
                .map(RetrieveUserFavorBoxVo::from)
                .toList();
    }
}
