package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
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
        neighborAdaptor
                .queryByFromUserIdAndToUserId(userId, toUserId)
                .ifPresent(neighborAdaptor::delete);
        neighborAdaptor
                .queryByFromUserIdAndToUserId(toUserId, userId)
                .ifPresent(neighborAdaptor::delete);
    }
}
