package tify.server.api.user.model.dto.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.DetailCategory;
import tify.server.domain.domains.user.domain.LargeCategory;
import tify.server.domain.domains.user.domain.SmallCategory;
import tify.server.domain.domains.user.vo.UserFavorVo;

@Getter
@Builder
public class UserFavorBoxVo {

    @Schema(description = "유저 취향 박스 내 취향의 pk값입니다.", example = "1")
    private final Long userFavorId;

    @Schema(description = "유저 취향 박스 내 취향의 대분류입니다.", example = "BEAUTY")
    private final LargeCategory largeCategory;

    @Schema(description = "유저 취향 박스 내 취향의 중분류입니다.", example = "MAKEUP")
    private final SmallCategory smallCategory;

    @Schema(description = "유저 취향 박스 내 취향의 소분류입니다.", example = "LIP")
    private final DetailCategory detailCategory;

    public static UserFavorBoxVo from(UserFavorVo userFavorVo) {
        return UserFavorBoxVo.builder()
            .userFavorId(userFavorVo.getUserFavor().getId())
            .largeCategory(userFavorVo.getLargeCategory())
            .smallCategory(userFavorVo.getUserFavor().getDetailCategory().getSmallCategory())
            .detailCategory(userFavorVo.getUserFavor().getDetailCategory())
            .build();
    }
}