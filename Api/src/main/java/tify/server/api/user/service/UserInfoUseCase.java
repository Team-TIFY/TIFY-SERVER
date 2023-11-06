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
import tify.server.domain.domains.user.domain.UserBlock;
import tify.server.domain.domains.user.vo.UserInfoVo;
import tify.server.domain.domains.user.vo.UserProfileVo;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInfoUseCase {

    private final UserAdaptor userAdaptor;
    private final NeighborAdaptor neighborAdaptor;
    private final UserBlockAdaptor userBlockAdaptor;

    public UserProfileVo execute(Long userId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Long searchedUserId = userAdaptor.query(userId).getId();
        Optional<Neighbor> neighbor =
                neighborAdaptor.queryByFromUserIdAndToUserId(currentUserId, searchedUserId);
        Optional<UserBlock> userBlock =
                userBlockAdaptor.queryByFromUserIdAndToUserId(currentUserId, searchedUserId);
        return UserProfileVo.of(
                userAdaptor.query(userId), neighbor.isPresent(), userBlock.isPresent());
    }

    public UserInfoVo executeByToken() {
        return userAdaptor.query(SecurityUtils.getCurrentUserId()).toUserInfoVo();
    }
}
