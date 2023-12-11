package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class UserFavorUpdateException extends BaseException {

    public static final BaseException EXCEPTION = new UserFavorUpdateException();

    private UserFavorUpdateException() {
        super(UserException.USER_FAVOR_UPDATE_ERROR);
    }
}
