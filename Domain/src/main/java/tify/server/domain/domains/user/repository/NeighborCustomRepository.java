package tify.server.domain.domains.user.repository;


import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import tify.server.domain.domains.user.dto.condition.NeighborCondition;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborDTO;

public interface NeighborCustomRepository {

    List<RetrieveNeighborDTO> searchNeighbors(NeighborCondition neighborCondition);

    List<RetrieveNeighborDTO> searchBirthdayNeighbors(NeighborCondition neighborCondition);

    Slice<RetrieveNeighborDTO> searchNeighborsToPage(
            NeighborCondition neighborCondition, Pageable pageable);
}
