package tify.server.api.user.model.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatchNeighborsOrdersRequest {

    @Schema(description = "순서 바꾸는 주체가 되는 친구 목록 id")
    @NotNull(message = "주체가 되는 친구 목록 id를 입력해주세요.")
    private Long fromNeighborId;

    @Schema(description = "순서 바꿈을 당하는 친구 목록 id")
    @NotNull(message = "순서가 바뀌어질 친구 목록 id를 입력해주세요.")
    private Long toNeighborId;
}
