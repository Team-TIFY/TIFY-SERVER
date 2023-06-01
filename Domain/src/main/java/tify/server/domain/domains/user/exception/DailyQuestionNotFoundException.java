package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class DailyQuestionNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new DailyQuestionNotFoundException();

    private DailyQuestionNotFoundException() {
        super(DailyQuestionException.DAILYQUESTION_NOT_FOUND_ERROR);
    }
}
