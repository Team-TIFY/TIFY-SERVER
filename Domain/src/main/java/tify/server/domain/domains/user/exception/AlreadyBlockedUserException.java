package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class AlreadyBlockedUserException extends BaseException {

    private static final BaseException EXCEPTION = new AlreadyBlockedUserException();

    private AlreadyBlockedUserException() {
        super(UserException.ALREADY_EXIST_USER_BLOCK_ERROR);
    }
}
