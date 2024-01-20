package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class UserResignNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new UserResignNotFoundException();

    private UserResignNotFoundException() {
        super(UserException.USER_RESIGN_NOT_FOUND_ERROR);
    }
}
