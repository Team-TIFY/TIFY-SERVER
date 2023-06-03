package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class UserFavorNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new UserFavorNotFoundException();

    private UserFavorNotFoundException() {
        super(UserException.USER_FAVOR_NOT_FOUND_ERROR);
    }
}
