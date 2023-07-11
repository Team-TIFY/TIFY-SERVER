package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class UserGenderNotFoundException extends BaseException {

    public static final BaseException Exception = new UserGenderNotFoundException();

    private UserGenderNotFoundException() {
        super(UserException.GENDER_NOT_FOUND_ERROR);
    }
}
