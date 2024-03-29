package tify.server.domain.domains.user.validator;

import static tify.server.domain.domains.user.exception.UserException.ALREADY_EXIST_USER_BLOCK_ERROR;
import static tify.server.domain.domains.user.exception.UserException.ALREADY_EXIST_USER_ERROR;
import static tify.server.domain.domains.user.exception.UserException.ALREADY_RESIGNED_USER_ERROR;
import static tify.server.domain.domains.user.exception.UserException.NOT_NEIGHBOR_ERROR;
import static tify.server.domain.domains.user.exception.UserException.USER_BLOCK_NOT_FOUND_ERROR;
import static tify.server.domain.domains.user.exception.UserException.USER_NOT_FOUND_ERROR;
import static tify.server.domain.domains.user.exception.UserException.USER_RESIGNED_ERROR;

import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Validator;
import tify.server.core.exception.BaseException;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.adaptor.UserBlockAdaptor;
import tify.server.domain.domains.user.adaptor.UserResignAdaptor;
import tify.server.domain.domains.user.domain.OauthInfo;

@Validator
@RequiredArgsConstructor
public class UserValidator {

    private final UserAdaptor userAdaptor;
    private final NeighborAdaptor neighborAdaptor;
    private final UserBlockAdaptor userBlockAdaptor;
    private final UserResignAdaptor userResignAdaptor;

    public void isValidUser(Long userId) {
        if (!userAdaptor.existByUserId(userId)) {
            throw new BaseException(USER_NOT_FOUND_ERROR);
        }
    }

    public void isNewUser(OauthInfo oauthInfo) {
        if (userAdaptor.existByOauthInfo(oauthInfo)) {
            throw new BaseException(ALREADY_EXIST_USER_ERROR);
        }
    }

    public void isNeighbor(Long userId, Long neighborId) {
        if (userResignAdaptor.optionalQueryByUserId(neighborId).isPresent()) {
            throw new BaseException(USER_RESIGNED_ERROR);
        }
        neighborAdaptor
                .queryByFromUserIdAndToUserId(userId, neighborId)
                .orElseThrow(() -> new BaseException(NOT_NEIGHBOR_ERROR));
    }

    public void isNewBlock(Long fromUserId, Long toUserId) {
        if (userBlockAdaptor.existsByFromUserIdAndToUserId(fromUserId, toUserId)) {
            throw new BaseException(ALREADY_EXIST_USER_BLOCK_ERROR);
        }
    }

    public void isBlocked(Long fromUserId, Long toUserId) {
        userBlockAdaptor
                .queryByFromUserIdAndToUserId(fromUserId, toUserId)
                .orElseThrow(() -> new BaseException(USER_BLOCK_NOT_FOUND_ERROR));
    }

    public void isNewResign(OauthInfo oauthInfo) {
        if (userResignAdaptor.existByOauthInfo(oauthInfo)) {
            throw new BaseException(ALREADY_RESIGNED_USER_ERROR);
        }
    }

    public void isResignedUser(Long userId) {
        if (userResignAdaptor.existsByUserId(userId)) {
            throw new BaseException(USER_RESIGNED_ERROR);
        }
    }

    public void isResignedUser(OauthInfo oauthInfo) {
        if (userResignAdaptor.existByOauthInfo(oauthInfo)) {
            throw new BaseException(USER_RESIGNED_ERROR);
        }
    }
}
