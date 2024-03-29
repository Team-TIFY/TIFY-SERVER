package tify.server.api.question.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.question.domain.DailyQuestion;
import tify.server.domain.domains.question.domain.DailyQuestionCategory;

@Getter
@Builder
public class DailyQuestionInfoVo {

    @Schema(description = "데일리 질문 id", example = "1")
    private final Long questionId;

    @Schema(description = "데일리 질문 내용", example = "점심 먹고 난 뒤 가장 자주 마시는 음료는?")
    private final String content;

    @Schema(description = "데일리 질문 카테고리", example = "PERFUME")
    private final DailyQuestionCategory category;

    @Schema(description = "데일리 질문 활성화 날짜")
    private final LocalDate loadingData;

    public static DailyQuestionInfoVo from(DailyQuestion dailyQuestion) {
        return DailyQuestionInfoVo.builder()
                .questionId(dailyQuestion.getId())
                .content(dailyQuestion.getContent())
                .category(dailyQuestion.getCategory())
                .loadingData(dailyQuestion.getLoadingDate())
                .build();
    }
}
