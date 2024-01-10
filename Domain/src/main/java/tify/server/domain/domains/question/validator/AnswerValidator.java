package tify.server.domain.domains.question.validator;


import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Validator;
import tify.server.domain.domains.question.adaptor.AnswerAdaptor;
import tify.server.domain.domains.question.exception.AnswerNotFoundException;

@Validator
@RequiredArgsConstructor
public class AnswerValidator {

    private final AnswerAdaptor answerAdaptor;

    public void isValidAnswer(Long answerId) {
        if (!answerAdaptor.existByAnswerId(answerId)) {
            throw AnswerNotFoundException.EXCEPTION;
        }
    }
}
