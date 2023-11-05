package tify.server.api.user.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.utils.UserUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserTag;
import tify.server.domain.domains.user.vo.UserTagVo;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFavorUseCase {

    private final UserUtils userUtils;
    private final UserAdaptor userAdaptor;

    public List<UserTagVo> execute(Long userId) {
        // 유저를 끌고온다
        User user = userAdaptor.query(userId);
        // 유저에 딸린 usertag를 갖고온다
        List<UserTag> userTags = user.getUserTags();
        // 그 list를 리턴한다
        return userTags.stream().map(UserTagVo::from).toList();
    }
}
