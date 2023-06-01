package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class UserTagNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new UserTagNotFoundException();

    private UserTagNotFoundException() {
        super(UserException.USER_TAG_NOT_FOUND_ERROR);
    }
}
