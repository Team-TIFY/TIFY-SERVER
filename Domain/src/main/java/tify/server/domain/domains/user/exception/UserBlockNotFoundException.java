package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class UserBlockNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new UserBlockNotFoundException();

    private UserBlockNotFoundException() {
        super(UserException.USER_BLOCK_NOT_FOUND_ERROR);
    }
}
