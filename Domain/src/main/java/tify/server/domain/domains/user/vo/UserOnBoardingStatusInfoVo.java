package tify.server.domain.domains.user.vo;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class UserOnBoardingStatusInfoVo {

    private final Long onBoardingStatusId;

    private final String name;

    @QueryProjection
    public UserOnBoardingStatusInfoVo(Long onBoardingStatusId, String name) {
        this.onBoardingStatusId = onBoardingStatusId;
        this.name = name;
    }
}
