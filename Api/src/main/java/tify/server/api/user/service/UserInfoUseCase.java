package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import tify.server.api.utils.UserUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.common.vo.UserInfoVo;
import tify.server.domain.domains.user.domain.User;

@UseCase
@RequiredArgsConstructor
public class UserInfoUseCase {

    private final UserUtils userUtils;

    public UserInfoVo execute() {
        User currentUser = userUtils.getUser();
        return currentUser.toUserInfoVo();
    }
}
