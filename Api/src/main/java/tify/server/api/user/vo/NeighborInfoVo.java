package tify.server.api.user.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.dto.model.NeighborVo;

@Getter
@Builder
public class NeighborInfoVo {

    @Schema(description = "친구 id", example = "1")
    private Long neighborId;

    @Schema(description = "친구 프로필 사진")
    private String neighborThumbnail;

    @Schema(description = "친구 이름")
    private String neighborName;

    @Schema(description = "친구 생일")
    private String neighborBirth;

    @Schema(description = "친구 온보딩 상태의 name", example = "영화 감상 후 리뷰 쓰는 중 \uFE0F")
    private String onBoardingStatus;

    public static NeighborInfoVo from(NeighborVo neighborVo) {
        return NeighborInfoVo.builder()
                .neighborId(neighborVo.getNeighbor().getToUserId())
                .neighborThumbnail(neighborVo.getProfile().getBirth())
                .neighborName(neighborVo.getProfile().getUserName())
                .neighborBirth(neighborVo.getProfile().getBirth())
                .onBoardingStatus(neighborVo.getOnBoardingStatus().getName())
                .build();
    }
}
