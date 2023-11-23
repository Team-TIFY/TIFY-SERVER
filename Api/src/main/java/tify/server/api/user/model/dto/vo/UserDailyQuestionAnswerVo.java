package tify.server.api.user.model.dto.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import java.sql.Timestamp;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.question.domain.DailyQuestionCategory;

@Getter
@Builder
public class UserDailyQuestionAnswerVo {

    @Schema(description = "카테고리입니다.", example = "FOOD")
    private final DailyQuestionCategory dailyQuestionCategory;

    @Schema(description = "답변 개수입니다.", example = "1")
    private final Long count;
}
