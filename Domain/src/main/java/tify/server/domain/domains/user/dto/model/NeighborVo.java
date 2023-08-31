package tify.server.domain.domains.user.dto.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.domain.Profile;
import tify.server.domain.domains.user.domain.UserOnBoardingStatus;

@Getter
@AllArgsConstructor
public class NeighborVo {

    private final Neighbor neighbor;

    private final Profile profile;

    private final UserOnBoardingStatus onBoardingStatus;
}
