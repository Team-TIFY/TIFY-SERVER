package tify.server.domain.domains.user.repository;


import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborDTO;

public interface NeighborCustomRepository {

    List<RetrieveNeighborDTO> searchNeighbors(Long userId);

    List<RetrieveNeighborDTO> searchBirthdayNeighbors(Long userId);

    Slice<RetrieveNeighborDTO> searchNeighborsToPage(Long userId, Pageable pageable);
}
