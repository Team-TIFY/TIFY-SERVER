package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class UserOpinionNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new UserOpinionNotFoundException();

    private UserOpinionNotFoundException() {
        super(UserException.USER_OPINION_NOT_FOUND_ERROR);
    }
}
