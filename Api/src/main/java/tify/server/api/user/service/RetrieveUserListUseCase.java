package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import tify.server.core.annotation.UseCase;
import tify.server.domain.common.vo.UserInfoVo;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.dto.condition.UserCondition;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrieveUserListUseCase {

    private final UserAdaptor userAdaptor;

    public Slice<UserInfoVo> execute(Pageable pageable, UserCondition condition) {
        return userAdaptor.searchUsers(pageable, condition).map(UserInfoVo::from);
    }
}
