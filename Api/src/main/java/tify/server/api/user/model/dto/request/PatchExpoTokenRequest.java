package tify.server.api.user.model.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatchExpoTokenRequest {

    @Schema(description = "유저에 할당할 expo token입니다.", example = "ExponentPushToken[exampleToken]")
    @NotNull(message = "expo token을 입력해주세요.")
    private String expoToken;
}
