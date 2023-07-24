package tify.server.core.exception;

public class DateFormatException extends BaseException {

    public static final BaseException EXCEPTION = new DateFormatException();

    private DateFormatException() {
        super(GlobalException.DATE_FORMAT_ERROR);
    }
}
