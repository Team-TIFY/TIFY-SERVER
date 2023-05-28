package tify.server.core.exception;

public class InternalServerException extends BaseException {

    public static final BaseException EXCEPTION = new InternalServerException();

    private InternalServerException() {
        super(GlobalException.INTERNAL_SERVER_ERROR);
    }
}
