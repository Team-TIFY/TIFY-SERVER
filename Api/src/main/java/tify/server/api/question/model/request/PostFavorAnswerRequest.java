package tify.server.api.question.model.request;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.question.dto.model.FavorAnswerDto;

@Getter
@NoArgsConstructor
public class PostFavorAnswerRequest {

    @NotBlank(message = "취향 질문 카테고리는 필수입니다.")
    private String categoryName;

    @Valid
    @Schema(description = "취향 질문에 대한 답변")
    @Size(min = 0, message = "적어도 1개의 답변은 입력해주세요.")
    private List<FavorAnswerDto> favorAnswerDtos;
}
