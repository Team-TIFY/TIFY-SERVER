package tify.server.api.user.model.dto.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.DetailCategory;
import tify.server.domain.domains.user.domain.UserFavor;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborFavorBoxDTO;

@Getter
@Builder
public class RetrieveUserFavorBoxVo {

    @Schema(description = "친구의 pk값입니다.", example = "1")
    private final Long neighborId;

    @Schema(description = "친구의 프로필 이미지입니다.")
    private final String neighborThumbnail;

    @Schema(description = "친구의 생일입니다.", example = "19990101")
    private final String neighborBirth;

    @Schema(description = "친구의 이름입니다.", example = "홍길동")
    private final String neighborName;

    @Schema(description = "친구의 취향 상자 정보입니다.", example = "PLACE, TOP, FAS_PRODUCT")
    private final List<DetailCategory> userFavorList;

    @Schema(description = "친구의 온보딩 상태 정보입니다.")
    private final String onBoardingStatus;

    @Schema(description = "친구가 정보를 업데이트한 시점입니다.")
    private final Timestamp updatedAt;

    @Schema(description = "친구의 정보 업데이트를 조회한 시점입니다.")
    private final Timestamp viewedAt;

    public static RetrieveUserFavorBoxVo from(RetrieveNeighborFavorBoxDTO dto) {
        return RetrieveUserFavorBoxVo.builder()
                .neighborId(dto.getUser().getId())
                .neighborThumbnail(dto.getUser().getProfile().getThumbNail())
                .neighborBirth(dto.getUser().getProfile().getBirth())
                .neighborName(dto.getUser().getProfile().getUserName())
                .userFavorList(
                        dto.getUser().getUserFavors().stream()
                                .map(UserFavor::getDetailCategory)
                                .toList())
                .onBoardingStatus(dto.getUser().getOnBoardingStatus().getName())
                .updatedAt(dto.getUser().getUpdatedAt())
                .viewedAt(dto.getViewedAt())
                .build();
    }
}
