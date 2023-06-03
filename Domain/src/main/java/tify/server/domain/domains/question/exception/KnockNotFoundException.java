package tify.server.domain.domains.question.exception;


import tify.server.core.exception.BaseException;

public class KnockNotFoundException extends BaseException {
    public static final BaseException EXCEPTION = new KnockNotFoundException();

    private KnockNotFoundException() {
        super(DailyQuestionException.KNOCK_NOT_FOUND_ERROR);
    }
}
