package tify.server.api.question.model.response;


import lombok.Builder;
import lombok.Getter;
import tify.server.api.question.model.vo.DailyQuestionInfoVo;

@Getter
@Builder
public class GetDailyQuestionResponse {

    private final DailyQuestionInfoVo dailyQuestionInfo;

    public static GetDailyQuestionResponse from(DailyQuestionInfoVo dailyQuestionInfo) {
        return GetDailyQuestionResponse.builder().dailyQuestionInfo(dailyQuestionInfo).build();
    }
}
