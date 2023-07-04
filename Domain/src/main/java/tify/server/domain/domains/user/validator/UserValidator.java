package tify.server.domain.domains.user.validator;

import static tify.server.domain.domains.user.exception.UserException.ALREADY_EXIST_USER_ERROR;

import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Validator;
import tify.server.core.exception.BaseException;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.OauthInfo;

@Validator
@RequiredArgsConstructor
public class UserValidator {

    private final UserAdaptor userAdaptor;

    public void isNewUser(OauthInfo oauthInfo) {
        if (userAdaptor.existByOauthInfo(oauthInfo)) {
            throw new BaseException(ALREADY_EXIST_USER_ERROR);
        }
    }
}
