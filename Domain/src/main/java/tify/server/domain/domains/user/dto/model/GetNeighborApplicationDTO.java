package tify.server.domain.domains.user.dto.model;


import lombok.Builder;
import lombok.Getter;
import tify.server.domain.common.vo.UserInfoVo;
import tify.server.domain.domains.user.domain.NeighborApplicationStatus;

@Getter
@Builder
public class GetNeighborApplicationDTO {

    private final Long neighborApplicationId;

    private final UserInfoVo toUserInfo;

    private final int mutualNeighborCounts;

    private final NeighborApplicationStatus neighborApplicationStatus;

    public static GetNeighborApplicationDTO of(
            RetrieveNeighborApplicationDTO dto, int mutualNeighborCounts) {
        return GetNeighborApplicationDTO.builder()
                .neighborApplicationId(dto.getNeighborApplicationId())
                .toUserInfo(UserInfoVo.from(dto.getUser()))
                .mutualNeighborCounts(mutualNeighborCounts)
                .neighborApplicationStatus(dto.getNeighborApplicationStatus())
                .build();
    }
}
