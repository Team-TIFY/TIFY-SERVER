package tify.server.domain.domains.question.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.core.annotation.DomainService;
import tify.server.domain.domains.question.adaptor.AnswerAdaptor;
import tify.server.domain.domains.question.adaptor.KnockAdaptor;
import tify.server.domain.domains.question.domain.Answer;
import tify.server.domain.domains.question.domain.Knock;
import tify.server.domain.domains.question.validator.QuestionValidator;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyQuestionDomainService {

    private final AnswerAdaptor answerAdaptor;
    private final KnockAdaptor knockAdaptor;
    private final QuestionValidator questionValidator;

    @Transactional
    public List<Knock> createAnswer(Long questionId, Long userId, String answer) {
        // 답변 가능 여부 검증
        questionValidator.isValidateAnswerToQuestion(questionId, userId);

        // 답변 작성
        answerAdaptor.save(
                Answer.builder().questionId(questionId).userId(userId).content(answer).build());

        return knockAdaptor.queryAllByKnockedUserIdAndDailyQuestionId(userId, questionId);
    }
}
