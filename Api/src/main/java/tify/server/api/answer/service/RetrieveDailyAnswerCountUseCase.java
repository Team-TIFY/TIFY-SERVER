package tify.server.api.answer.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.answer.model.vo.RetrieveAnswerCountVo;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.AnswerAdaptor;
import tify.server.domain.domains.question.adaptor.DailyQuestionAdaptor;
import tify.server.domain.domains.question.domain.DailyQuestion;

@UseCase
@RequiredArgsConstructor
public class RetrieveDailyAnswerCountUseCase {

    private final AnswerAdaptor answerAdaptor;
    private final DailyQuestionAdaptor dailyQuestionAdaptor;

    @Transactional(readOnly = true)
    public RetrieveAnswerCountVo execute(Long questionId) {
        DailyQuestion dailyQuestion = dailyQuestionAdaptor.query(questionId);
        Long answerCount = answerAdaptor.queryAnswerCount(questionId);
        return RetrieveAnswerCountVo.of(answerCount);
    }
}
