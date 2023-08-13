package tify.server.domain.domains.question.dto.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class FavorAnswerDto {
  
  @NotNull
  private Long num;
  
  @NotBlank(message = "답변 내용을 입력해주세요.")
  private String answer;
}
