package tify.server.api.user.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.domain.NeighborApplication;
import tify.server.domain.domains.user.exception.AlreadyExistNeighborRelationshipException;
import tify.server.domain.domains.user.validator.NeighborValidator;

@UseCase
@RequiredArgsConstructor
@Transactional
public class AcceptanceNeighborApplicationUseCase {

    private final NeighborAdaptor neighborAdaptor;
    private final NeighborValidator neighborValidator;

    public void execute(Long neighborApplicationId) {

        // 나 (친구 신청을 받은 사람)
        Long currentUserId = SecurityUtils.getCurrentUserId();
        NeighborApplication neighborApplication =
                neighborAdaptor.queryNeighborApplication(neighborApplicationId);

        neighborValidator.userIdAndNeighborApplicationValidate(neighborApplication, currentUserId);

        neighborApplication.accept();

        // 상대방 (친구 신청을 보낸 사람)
        Long toUserId = neighborApplication.getFromUserId();

        // 친구 맺기 (toUserId, fromUserId inversion)
        if (neighborAdaptor.existsNeighbor(currentUserId, toUserId)) {
            throw AlreadyExistNeighborRelationshipException.EXCEPTION;
        }

        List<Neighbor> neighbors = neighborAdaptor.queryAllByFromUserId(currentUserId);

        neighborAdaptor.save(
                Neighbor.builder()
                        .fromUserId(currentUserId)
                        .toUserId(toUserId)
                        .isView(true)
                        .order((long) neighbors.size() + 1L)
                        .build());
    }
}
