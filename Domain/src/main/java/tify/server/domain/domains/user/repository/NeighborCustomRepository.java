package tify.server.domain.domains.user.repository;


import java.util.List;
import tify.server.domain.domains.user.dto.condition.NeighborCondition;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborDTO;

public interface NeighborCustomRepository {

    List<RetrieveNeighborDTO> searchToPage(NeighborCondition neighborCondition);

    List<RetrieveNeighborDTO> searchBirthToPage(NeighborCondition neighborCondition);
}
