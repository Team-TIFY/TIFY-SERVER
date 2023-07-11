package tify.server.api.user.model.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PutUserProfileRequest {

    @Schema(description = "수정할 사용자의 이름", example = "asdf")
    @NotBlank(message = "사용자 이름을 입력해주세요.")
    private String username;

    @Schema(description = "수정할 사용자의 생일", example = "19990606")
    @NotBlank(message = "사용자 생년월일을 입력해주세요.")
    private String birth;

    @Schema(description = "수정할 사용자의 직업", example = "간호사")
    @NotBlank(message = "사용자 직업을 입력해주세요.")
    private String job;

    @Schema(description = "수정할 사용자의 성별", example = "MALE")
    @NotBlank(message = "사용자 성별을 입력해주세요.")
    private String gender;

    @Schema(description = "수정할 사용자의 프로필 이미지", example = "test.com")
    @NotBlank(message = "사용자 프로필 이미지를 입력해주세요.")
    private String thumbnail;
}
