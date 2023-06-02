package tify.server.domain.domains.user.exception;

import tify.server.core.exception.BaseException;

public class AlarmHistoryNotFoundException extends BaseException {
    public static final BaseException EXCEPTION = new AlarmHistoryNotFoundException();

    private AlarmHistoryNotFoundException() {
        super(AlarmHistoryException.ALARM_NOT_FOUND_ERROR);}
}
