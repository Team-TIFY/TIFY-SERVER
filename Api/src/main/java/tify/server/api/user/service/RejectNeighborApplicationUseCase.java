package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.domain.NeighborApplication;
import tify.server.domain.domains.user.exception.NeighborNotFoundException;
import tify.server.domain.domains.user.validator.NeighborValidator;

@UseCase
@RequiredArgsConstructor
@Transactional
public class RejectNeighborApplicationUseCase {

    private final NeighborAdaptor neighborAdaptor;
    private final NeighborValidator neighborValidator;

    public void execute(Long neighborApplicationId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        NeighborApplication neighborApplication =
                neighborAdaptor.queryNeighborApplication(neighborApplicationId);

        neighborValidator.userIdAndNeighborApplicationValidate(neighborApplication, currentUserId);

        neighborApplication.reject();

        Neighbor neighbor =
                neighborAdaptor
                        .queryByFromUserIdAndToUserId(
                                neighborApplication.getFromUserId(), currentUserId)
                        .orElseThrow(() -> NeighborNotFoundException.EXCEPTION);
        neighborAdaptor.delete(neighbor);
    }
}
