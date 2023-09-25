package tify.server.domain.domains.user.repository;


import org.springframework.data.domain.Slice;
import tify.server.domain.domains.user.dto.condition.NeighborCondition;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborDTO;

public interface NeighborCustomRepository {

    Slice<RetrieveNeighborDTO> searchToPage(NeighborCondition neighborCondition);

    Slice<RetrieveNeighborDTO> searchBirthToPage(NeighborCondition neighborCondition);
}
