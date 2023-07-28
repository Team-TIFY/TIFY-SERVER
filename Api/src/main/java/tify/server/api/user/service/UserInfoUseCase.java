package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.common.vo.UserInfoVo;
import tify.server.domain.common.vo.UserProfileVo;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.User;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInfoUseCase {

    private final UserAdaptor userAdaptor;

    public UserProfileVo execute(Long userId) {
        User user = userAdaptor.query(userId);
        return user.toUserProfileVo();
    }

    public UserInfoVo executeByToken() {
        return userAdaptor.query(SecurityUtils.getCurrentUserId()).toUserInfoVo();
    }
}
