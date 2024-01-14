package tify.server.domain.domains.alarm.exception;


import tify.server.core.exception.BaseException;

public class ExpoPushTicketException extends BaseException {

    public static final BaseException EXCEPTION = new ExpoPushTicketException();

    private ExpoPushTicketException() {
        super(AlarmHistoryException.EXPO_PUSH_TICKET_ERROR);
    }
}
