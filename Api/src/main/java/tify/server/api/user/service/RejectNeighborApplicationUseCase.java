package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.domain.NeighborApplication;
import tify.server.domain.domains.user.validator.NeighborValidator;
import tify.server.domain.domains.user.validator.UserValidator;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RejectNeighborApplicationUseCase {

    private final NeighborAdaptor neighborAdaptor;
    private final NeighborValidator neighborValidator;
    private final UserValidator userValidator;

    public void execute(Long neighborApplicationId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        NeighborApplication neighborApplication =
                neighborAdaptor.queryNeighborApplication(neighborApplicationId);

        userValidator.isValidUser(neighborApplication.getFromUserId());

        neighborValidator.userIdAndNeighborApplicationValidate(neighborApplication, currentUserId);

        neighborApplication.reject();
    }
}
