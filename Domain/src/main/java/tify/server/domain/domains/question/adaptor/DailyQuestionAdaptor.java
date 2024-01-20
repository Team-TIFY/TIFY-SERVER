package tify.server.domain.domains.question.adaptor;


import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.question.domain.DailyQuestion;
import tify.server.domain.domains.question.exception.DailyQuestionNotFoundException;
import tify.server.domain.domains.question.repository.DailyQuestionRepository;

@Adaptor
@RequiredArgsConstructor
public class DailyQuestionAdaptor {

    private final DailyQuestionRepository dailyQuestionRepository;

    public DailyQuestion query(Long dailyQuestionId) {
        return dailyQuestionRepository
                .findById(dailyQuestionId)
                .orElseThrow(() -> DailyQuestionNotFoundException.EXCEPTION);
    }

    public DailyQuestion save(DailyQuestion dailyQuestion) {
        return dailyQuestionRepository.save(dailyQuestion);
    }

    public DailyQuestion queryByLoadingDate(LocalDate loadingDate) {
        return dailyQuestionRepository
                .findByLoadingDate(loadingDate)
                .orElseThrow(() -> DailyQuestionNotFoundException.EXCEPTION);
    }

    public Boolean existByQuestionId(Long dailyQuestionId) {
        return dailyQuestionRepository.existsById(dailyQuestionId);
    }
}
