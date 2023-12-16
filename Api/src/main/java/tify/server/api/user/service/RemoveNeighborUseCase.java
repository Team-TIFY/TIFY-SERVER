package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.exception.NeighborNotFoundException;
import tify.server.domain.domains.user.validator.UserValidator;

@UseCase
@RequiredArgsConstructor
public class RemoveNeighborUseCase {

    private final NeighborAdaptor neighborAdaptor;
    private final UserValidator userValidator;

    @Transactional
    public void execute(Long toUserId) {
        Long userId = SecurityUtils.getCurrentUserId();
        userValidator.isNeighbor(userId, toUserId);

        Neighbor fromNeighbor =
                neighborAdaptor
                        .queryByFromUserIdAndToUserId(userId, toUserId)
                        .orElseThrow(() -> NeighborNotFoundException.EXCEPTION);
        Neighbor toNeighbor =
                neighborAdaptor
                        .queryByFromUserIdAndToUserId(toUserId, userId)
                        .orElseThrow(() -> NeighborNotFoundException.EXCEPTION);

        neighborAdaptor.delete(fromNeighbor);
        neighborAdaptor.delete(toNeighbor);
    }
}
