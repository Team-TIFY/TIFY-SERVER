package tify.server.api.user.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.domain.NeighborApplication;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.exception.AlreadyExistNeighborRelationshipException;
import tify.server.domain.domains.user.exception.SelfNeighborException;

@UseCase
@RequiredArgsConstructor
@Transactional
public class CreateNeighborUseCase {

    private final UserAdaptor userAdaptor;
    private final NeighborAdaptor neighborAdaptor;

    public void execute(Long toUserId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        if (currentUserId.equals(toUserId)) {
            throw SelfNeighborException.EXCEPTION;
        }

        User toUser = userAdaptor.query(toUserId);

        if (neighborAdaptor.existsNeighbor(currentUserId, toUser.getId())) {
            throw AlreadyExistNeighborRelationshipException.EXCEPTION;
        }
        List<Neighbor> neighbors = neighborAdaptor.queryAllByFromUserId(currentUserId);

        neighborAdaptor.save(
                Neighbor.builder()
                        .fromUserId(currentUserId)
                        .toUserId(toUser.getId())
                        .isView(true)
                        .order((long) neighbors.size() + 1L)
                        .build());

        // 반대편에서 이미 친구가 되어있다면 신청을 보내지 않음
        if (!neighborAdaptor
                .queryByFromUserIdAndToUserId(toUser.getId(), currentUserId)
                .isPresent()) {
            neighborAdaptor.saveNeighborApplication(
                    NeighborApplication.builder()
                            .fromUserId(SecurityUtils.getCurrentUserId())
                            .toUserId(toUserId)
                            .build());
        }
    }
}
