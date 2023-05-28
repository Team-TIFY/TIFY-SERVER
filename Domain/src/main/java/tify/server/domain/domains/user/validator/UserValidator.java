package tify.server.domain.domains.user.validator;


import tify.server.core.annotation.Validator;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.OauthInfo;
import tify.server.domain.domains.user.exception.AlreadyExistUserException;
import lombok.RequiredArgsConstructor;

@Validator
@RequiredArgsConstructor
public class UserValidator {

    private final UserAdaptor userAdaptor;

    public void isNewUser(OauthInfo oauthInfo) {
        if (userAdaptor.existByOauthInfo(oauthInfo)) {
            throw AlreadyExistUserException.EXCEPTION;
        }
    }
}
