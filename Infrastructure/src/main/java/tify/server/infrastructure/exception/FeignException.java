package tify.server.infrastructure.exception;


import tify.server.core.exception.BaseException;
import tify.server.core.exception.GlobalException;

public class FeignException extends BaseException {

    public static final BaseException EXCEPTION = new FeignException();

    private FeignException() {
        super(GlobalException.FEIGN_SERVER_ERROR);
    }
}
