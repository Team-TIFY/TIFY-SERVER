package tify.server.domain.domains.alarm.exception;


import tify.server.core.exception.BaseException;

public class NotValidExpoTokenException extends BaseException {

    public static final BaseException EXCEPTION = new NotValidExpoTokenException();

    private NotValidExpoTokenException() {
        super(AlarmHistoryException.NOT_VALID_EXPO_TOKEN_ERROR);
    }
}
