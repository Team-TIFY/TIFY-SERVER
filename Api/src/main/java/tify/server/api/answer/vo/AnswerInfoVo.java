package tify.server.api.answer.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.question.domain.Answer;

@Getter
@Builder
public class AnswerInfoVo {

    @Schema(description = "답변 id", example = "1")
    private Long id;

    @Schema(description = "답변을 한 질문의 id", example = "1")
    private Long questionId;

    @Schema(description = "답변을 한 유저의 id", example = "1")
    private Long userId;

    @Schema(description = "답변 내용", example = "content")
    private String content;

    public static AnswerInfoVo from(Answer answer) {
        return AnswerInfoVo.builder()
                .id(answer.getId())
                .questionId(answer.getQuestionId())
                .userId(answer.getUserId())
                .content(answer.getContent())
                .build();
    }
}
