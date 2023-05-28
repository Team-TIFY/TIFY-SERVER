package tify.server.core.exception;

public class ExpiredTokenException extends BaseException {

    public static final BaseException EXCEPTION = new ExpiredTokenException();

    private ExpiredTokenException() {
        super(GlobalException.EXPIRED_TOKEN_ERROR);
    }
}
