package tify.server.domain.domains.question.dto.model;


import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FavorAnswerDto {

    @NotNull(message = "카테고리별 질문 순서를 입력해주세요.")
    private int num;

    private String answer;
}
