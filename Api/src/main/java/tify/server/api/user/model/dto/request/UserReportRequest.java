package tify.server.api.user.model.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserReportRequest {

    @Schema(description = "신고의 제목입니다.")
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @Schema(description = "신고할 유저의 아이디입니다.")
    @NotBlank(message = "신고할 유저의 아이디를 입력해주세요.")
    private String userId;

    @Schema(description = "유저에 대한 신고 내용입니다.")
    @NotBlank(message = "신고 내용을 입력해주세요.")
    private String content;
}
