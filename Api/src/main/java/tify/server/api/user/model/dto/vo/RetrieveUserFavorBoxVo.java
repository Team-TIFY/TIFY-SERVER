package tify.server.api.user.model.dto.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.DetailCategory;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserFavor;

@Getter
@Builder
public class RetrieveUserFavorBoxVo {

    @Schema(description = "친구의 pk값입니다.", example = "1")
    private final Long id;

    @Schema(description = "친구의 프로필 이미지입니다.")
    private final String thumbnail;

    @Schema(description = "친구의 id입니다.", example = "@aaa")
    private final String userId;

    @Schema(description = "친구의 이름입니다.", example = "홍길동")
    private final String userName;

    @Schema(description = "친구의 취향 상자 정보입니다.", example = "PLACE, TOP, FAS_PRODUCT")
    private final List<DetailCategory> userFavorList;

    @Schema(description = "친구의 온보딩 상태 정보입니다.")
    private final String userOnBoardingStatus;

    public static RetrieveUserFavorBoxVo from(User user) {
        return RetrieveUserFavorBoxVo.builder()
                .id(user.getId())
                .thumbnail(user.getProfile().getThumbNail())
                .userId(user.getUserId())
                .userName(user.getProfile().getUserName())
                .userFavorList(
                        user.getUserFavors().stream().map(UserFavor::getDetailCategory).toList())
                .userOnBoardingStatus(user.getOnBoardingStatus().getName())
                .build();
    }
}
