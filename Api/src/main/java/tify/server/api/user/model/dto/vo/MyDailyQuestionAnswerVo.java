package tify.server.api.user.model.dto.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.question.dto.model.DailyQuestionAnswerVo;

@Getter
@Builder
public class MyDailyQuestionAnswerVo {

    @Schema(description = "답변한 날짜입니다.", example = "20000101")
    private final Timestamp answerTime;

    @Schema(description = "질문의 id(pk)값입니다.", example = "1")
    private final Long questionId;

    @Schema(description = "질문의 내용입니다.", example = "친구가 요리를 해줬는데 맛이 없다. 표정을 숨기지 못한다. vs 맛있다고 한다.")
    private final String question;

    @Schema(description = "답변의 id(pk)값입니다.", example = "1")
    private final Long answerId;

    @Schema(description = "답변의 내용입니다.", example = "맛있다고 한다.")
    private final String answer;

    public static MyDailyQuestionAnswerVo from(DailyQuestionAnswerVo dailyQuestionAnswerVo) {
        return MyDailyQuestionAnswerVo.builder()
                .answerTime(dailyQuestionAnswerVo.getDailyQuestion().getCreatedAt())
                .questionId(dailyQuestionAnswerVo.getDailyQuestion().getId())
                .question(dailyQuestionAnswerVo.getDailyQuestion().getContent())
                .answerId(dailyQuestionAnswerVo.getAnswer().getId())
                .answer(dailyQuestionAnswerVo.getAnswer().getContent())
                .build();
    }
}
