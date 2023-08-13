package tify.server.api.question.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.question.dto.model.FavorAnswerDto;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostFavorAnswerRequest {
  
  private String categoryName;
  
  @Schema(description = "취향 질문에 대한 답변")
  @NotBlank(message = "답변을 입력해주세요.")
  private List<FavorAnswerDto> favorAnswerDtos;
}
