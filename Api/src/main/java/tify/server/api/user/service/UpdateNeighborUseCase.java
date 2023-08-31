package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.domain.Neighbor;

@UseCase
@RequiredArgsConstructor
@Transactional
public class UpdateNeighborUseCase {

    private final NeighborAdaptor neighborAdaptor;

    public void executeToOrder(Long fromNeighborId, Long toNeighborId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Neighbor fromNeighbor = neighborAdaptor.queryByUserId(fromNeighborId, currentUserId);
        Neighbor toNeighbor = neighborAdaptor.queryByUserId(toNeighborId, currentUserId);
        long tmp = fromNeighbor.getOrder();

        fromNeighbor.updateOrder(toNeighbor.getOrder());
        toNeighbor.updateOrder(tmp);
    }

    public void executeToIsView(Long neighborId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Neighbor neighbor = neighborAdaptor.queryByUserId(neighborId, currentUserId);

        neighbor.updateIsView();
    }
}
