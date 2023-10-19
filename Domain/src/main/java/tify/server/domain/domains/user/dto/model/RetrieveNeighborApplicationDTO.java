package tify.server.domain.domains.user.dto.model;


import lombok.Getter;
import tify.server.domain.common.vo.UserInfoVo;
import tify.server.domain.domains.user.domain.NeighborApplicationStatus;
import tify.server.domain.domains.user.domain.User;

@Getter
public class RetrieveNeighborApplicationDTO {

    private final Long neighborApplicationId;

    private final UserInfoVo toUserInfo;

    private final NeighborApplicationStatus neighborApplicationStatus;

    public RetrieveNeighborApplicationDTO(
            Long neighborApplicationId,
            User toUser,
            NeighborApplicationStatus neighborApplicationStatus) {
        this.neighborApplicationId = neighborApplicationId;
        this.toUserInfo = UserInfoVo.from(toUser);
        this.neighborApplicationStatus = neighborApplicationStatus;
    }
}
