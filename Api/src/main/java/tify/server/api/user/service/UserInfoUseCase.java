package tify.server.api.user.service;


import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.adaptor.UserBlockAdaptor;
import tify.server.domain.domains.user.domain.Neighbor;
import tify.server.domain.domains.user.domain.NeighborApplication;
import tify.server.domain.domains.user.domain.UserBlock;
import tify.server.domain.domains.user.validator.UserValidator;
import tify.server.domain.domains.user.vo.UserInfoVo;
import tify.server.domain.domains.user.vo.UserProfileVo;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInfoUseCase {

    private final UserAdaptor userAdaptor;
    private final UserValidator userValidator;
    private final NeighborAdaptor neighborAdaptor;
    private final UserBlockAdaptor userBlockAdaptor;

    public UserProfileVo execute(Long searchedUserId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        userValidator.isResignedUser(searchedUserId);
        Optional<Neighbor> fromNeighbor =
                neighborAdaptor.queryByFromUserIdAndToUserId(currentUserId, searchedUserId);
        Optional<Neighbor> toNeighbor =
                neighborAdaptor.queryByFromUserIdAndToUserId(searchedUserId, currentUserId);
        Optional<UserBlock> userBlock =
                userBlockAdaptor.queryByFromUserIdAndToUserId(currentUserId, searchedUserId);
        Optional<NeighborApplication> receivedApplication =
                neighborAdaptor.optionalQueryByFromUserIdAndToUserId(searchedUserId, currentUserId);
        Optional<NeighborApplication> sentApplication =
                neighborAdaptor.optionalQueryByFromUserIdAndToUserId(currentUserId, searchedUserId);
        return UserProfileVo.of(
                userAdaptor.query(searchedUserId),
                fromNeighbor.isPresent() && toNeighbor.isPresent(),
                userBlock.isPresent(),
                receivedApplication.orElse(null),
                sentApplication.orElse(null));
    }

    public UserInfoVo executeByToken() {
        return userAdaptor.query(SecurityUtils.getCurrentUserId()).toUserInfoVo();
    }
}
