package tify.server.core.exception;

public class InvalidTokenException extends BaseException {

    public static final BaseException EXCEPTION = new InvalidTokenException();

    private InvalidTokenException() {
        super(GlobalException.INVALID_TOKEN_ERROR);
    }
}
