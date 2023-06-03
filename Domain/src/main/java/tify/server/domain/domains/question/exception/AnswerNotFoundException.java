package tify.server.domain.domains.question.exception;


import tify.server.core.exception.BaseException;

public class AnswerNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new AnswerNotFoundException();

    private AnswerNotFoundException() {
        super(DailyQuestionException.ANSWER_NOT_FOUND_ERROR);
    }
}
