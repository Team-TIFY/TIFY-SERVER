package tify.server.api.user.model.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.user.domain.DetailCategory;

@Getter
@NoArgsConstructor
public class UserOnBoardingRequest {

    @Size(max = 10, message = "이름은 최대 10글자입니다.")
    @NotBlank(message = "이름을 입력해주세요.")
    private String username;

    @Size(max = 15, message = "아이디는 최대 15글자입니다.")
    @NotBlank(message = "아이디를 입력해주세요.")
    private String id;

    @Schema(description = "포맷은 19990606입니다.")
    @NotBlank(message = "생일을 입력해주세요.")
    private String birth;

    @NotBlank(message = "성별을 입력해주세요.")
    private String gender;

    @NotBlank(message = "상태값을 입력해주세요.")
    private String onBoardingState;

    @Schema(description = "유저 취향 상자에 들어갈 소분류들입니다.")
    @NotNull
    private List<DetailCategory> userFavorDtoList;
}
