package tify.server.api.question.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.AnswerAdaptor;
import tify.server.domain.domains.question.adaptor.DailyQuestionAdaptor;
import tify.server.domain.domains.question.domain.DailyQuestion;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrieveDailyQuestionAnswerUseCase {

    private final DailyQuestionAdaptor dailyQuestionAdaptor;
    private final AnswerAdaptor answerAdaptor;

    public Boolean isAlreadyExistUserAnswer(Long dailyQuestionId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        DailyQuestion dailyQuestion = dailyQuestionAdaptor.query(dailyQuestionId);
        return answerAdaptor
                .optionalQueryByQuestionAndUser(dailyQuestionId, currentUserId)
                .isPresent();
    }
}
