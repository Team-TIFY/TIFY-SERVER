package tify.server.api.question.service;


import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import tify.server.api.question.model.vo.DailyQuestionInfoVo;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.DailyQuestionAdaptor;

@UseCase
@RequiredArgsConstructor
public class RetrieveDailyQuestionUseCase {

    private final DailyQuestionAdaptor dailyQuestionAdaptor;

    public DailyQuestionInfoVo execute(LocalDate loadingDate) {
        return DailyQuestionInfoVo.from(dailyQuestionAdaptor.queryByLoadingDate(loadingDate));
    }
}
