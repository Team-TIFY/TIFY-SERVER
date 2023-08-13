package tify.server.domain.domains.question.exception;


import tify.server.core.exception.BaseException;

public class NotValidTodayQuestionException extends BaseException {

    public static final BaseException EXCEPTION = new NotValidTodayQuestionException();

    private NotValidTodayQuestionException() {
        super(QuestionException.NOT_VALID_TODAY_QUESTION_ERROR);
    }
}
