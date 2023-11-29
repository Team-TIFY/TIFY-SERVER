package tify.server.api.answer.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Optional;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.question.domain.Answer;
import tify.server.domain.domains.question.dto.model.AnswerVo;

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

    public static AnswerInfoVo from(AnswerVo answer) {
        return AnswerInfoVo.builder()
                .id(answer.getAnswer().getId())
                .questionId(answer.getAnswer().getQuestionId())
                .userId(answer.getUser().getId())
                .content(answer.getAnswer().getContent())
                .build();
    }

    public static AnswerInfoVo from(Answer answer) {
        return AnswerInfoVo.builder()
                .id(Optional.ofNullable(answer).map(Answer::getId).orElse(null))
                .questionId(Optional.ofNullable(answer).map(Answer::getQuestionId).orElse(null))
                .userId(Optional.ofNullable(answer).map(Answer::getUserId).orElse(null))
                .content(Optional.ofNullable(answer).map(Answer::getContent).orElse(null))
                .build();
    }
}
