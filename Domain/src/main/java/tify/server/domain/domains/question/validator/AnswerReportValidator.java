package tify.server.domain.domains.question.validator;


import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Validator;
import tify.server.domain.domains.question.adaptor.AnswerAdaptor;
import tify.server.domain.domains.question.domain.Answer;
import tify.server.domain.domains.question.exception.NotValidAnswerReportException;

@Validator
@RequiredArgsConstructor
public class AnswerReportValidator {

    private final AnswerAdaptor answerAdaptor;

    public void isMyAnswer(Long userId, Long answerId) {
        Answer answer = answerAdaptor.query(answerId);
        if (answer.getUserId().equals(userId)) {
            throw NotValidAnswerReportException.EXCEPTION;
        }
    }
}
