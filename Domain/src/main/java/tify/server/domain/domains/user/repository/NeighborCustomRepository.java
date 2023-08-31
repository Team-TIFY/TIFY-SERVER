package tify.server.domain.domains.user.repository;


import org.springframework.data.domain.Slice;
import tify.server.domain.domains.user.dto.condition.NeighborCondition;
import tify.server.domain.domains.user.dto.model.NeighborVo;

public interface NeighborCustomRepository {

    Slice<NeighborVo> searchToPage(NeighborCondition neighborCondition);
}
