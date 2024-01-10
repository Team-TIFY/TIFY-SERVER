package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class AlreadyResignedUserException extends BaseException {

    public static final BaseException EXCEPTION = new AlreadyResignedUserException();

    private AlreadyResignedUserException() {
        super(UserException.ALREADY_RESIGNED_USER_ERROR);
    }
}
