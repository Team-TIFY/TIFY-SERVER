package tify.server.domain.domains.question.exception;


import tify.server.core.exception.BaseException;

public class DailyQuestionNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new DailyQuestionNotFoundException();

    private DailyQuestionNotFoundException() {
        super(DailyQuestionException.DAILY_QUESTION_NOT_FOUND_ERROR);
    }
}
