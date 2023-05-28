package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class UserNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new UserNotFoundException();

    private UserNotFoundException() {
        super(UserException.USER_NOT_FOUND_ERROR);
    }
}
