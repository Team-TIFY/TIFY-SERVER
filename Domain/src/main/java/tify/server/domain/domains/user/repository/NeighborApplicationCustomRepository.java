package tify.server.domain.domains.user.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborApplicationDTO;

public interface NeighborApplicationCustomRepository {

    Slice<RetrieveNeighborApplicationDTO> searchAllNeighborApplicationByFromUserId(
            Pageable pageable, Long toUserId);
}
