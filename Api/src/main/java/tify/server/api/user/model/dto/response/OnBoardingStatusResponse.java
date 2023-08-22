package tify.server.api.user.model.dto.response;


import java.util.List;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.vo.UserOnBoardingStatusInfoVo;

@Getter
@Builder
public class OnBoardingStatusResponse {

    private final List<UserOnBoardingStatusInfoVo> userOnBoardingStatusInfos;

    public static OnBoardingStatusResponse from(
            List<UserOnBoardingStatusInfoVo> userOnBoardingStatusInfos) {
        return OnBoardingStatusResponse.builder()
                .userOnBoardingStatusInfos(userOnBoardingStatusInfos)
                .build();
    }
}
