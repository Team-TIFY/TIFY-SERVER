package tify.server.api.user.model.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.user.domain.DetailCategory;

@Getter
@NoArgsConstructor
public class UserFavorDto {

    @Schema(description = "취향의 순서입니다.", example = "1")
    private Long order;

    @Schema(description = "취향의 종류입니다.", example = "LIP")
    private DetailCategory detailCategory;
}
