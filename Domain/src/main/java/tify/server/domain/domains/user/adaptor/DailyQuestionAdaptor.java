package tify.server.domain.domains.user.adaptor;


import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.user.domain.DailyQuestion;
import tify.server.domain.domains.user.exception.DailyQuestionNotFoundException;
import tify.server.domain.domains.user.repository.DailyQuestionRepository;

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
}
