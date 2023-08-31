package tify.server.api.user.model.dto.response;


import lombok.Builder;
import lombok.Getter;
import tify.server.api.user.vo.NeighborInfoVo;
import tify.server.domain.domains.user.dto.model.NeighborVo;

@Getter
@Builder
public class RetrieveNeighborDTO {

    private final NeighborInfoVo neighborInfo;

    public static RetrieveNeighborDTO of(NeighborVo neighborVo) {
        return RetrieveNeighborDTO.builder().neighborInfo(NeighborInfoVo.from(neighborVo)).build();
    }
}
