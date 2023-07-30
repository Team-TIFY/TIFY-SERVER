package tify.server.api.user.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.utils.UserUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.common.vo.UserFavorVo;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.adaptor.UserFavorAdaptor;
import tify.server.domain.domains.user.domain.LargeCategory;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserTag;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFavorFilterUseCase {

    private final UserUtils userUtils;
    private final UserAdaptor userAdaptor;
    private final UserFavorAdaptor userFavorAdaptor;

    public List<UserFavorVo> execute(Long userId, LargeCategory largeCategory) {
        // 유저를 끌고온다
        User user = userAdaptor.query(userId);
        // 유저의 Tag를 가져온다
        List<UserTag> userTagList = user.getUserTags();
        // largeCategory에 대한 UserFavorVo를 리턴한다
        return userFavorAdaptor.queryByLargeCategory(largeCategory);
    }
}
