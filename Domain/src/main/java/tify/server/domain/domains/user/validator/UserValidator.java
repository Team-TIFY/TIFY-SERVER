package tify.server.domain.domains.user.validator;

import static tify.server.domain.domains.user.exception.UserException.ALREADY_EXIST_USER_ERROR;
import static tify.server.domain.domains.user.exception.UserException.NOT_EXIST_NEIGHBOR_ERROR;

import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Validator;
import tify.server.core.exception.BaseException;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.domain.OauthInfo;

@Validator
@RequiredArgsConstructor
public class UserValidator {

    private final UserAdaptor userAdaptor;
    private final NeighborAdaptor neighborAdaptor;

    public void isNewUser(OauthInfo oauthInfo) {
        if (userAdaptor.existByOauthInfo(oauthInfo)) {
            throw new BaseException(ALREADY_EXIST_USER_ERROR);
        }
    }

    public void isNeighbor(Long userId, Long neighborId) {
        if (neighborAdaptor.queryAllByFromUserId(userId).stream()
                .map(Neighbor::getToUserId)
                .noneMatch(id -> id == neighborId)) {
            throw new BaseException(NOT_EXIST_NEIGHBOR_ERROR);
        }
    }
}
