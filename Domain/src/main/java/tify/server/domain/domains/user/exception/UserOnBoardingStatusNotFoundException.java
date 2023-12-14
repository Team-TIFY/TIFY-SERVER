package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class UserOnBoardingStatusNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new UserOnBoardingStatusNotFoundException();

    private UserOnBoardingStatusNotFoundException() {
        super(UserException.USER_ON_BOARDING_STATUS_NOT_FOUND_ERROR);
    }
}
