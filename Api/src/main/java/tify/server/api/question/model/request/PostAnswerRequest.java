package tify.server.api.question.model.request;


import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostAnswerRequest {

    @Schema(description = "답변")
    @NotBlank(message = "답변을 입력해주세요.")
    @Size(max = 40, message = "최대 40자까지만 입력 가능합니다.")
    private String answer;
}
