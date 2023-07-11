package tify.server.api.user.service;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.api.utils.UserUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.common.vo.UserFavorVo;
import tify.server.domain.domains.user.domain.UserFavor;
import tify.server.domain.domains.user.domain.UserTag;

@UseCase
@RequiredArgsConstructor
public class UserFavorUseCase {

    private final UserUtils userUtils;

    public List<UserFavorVo> execute() {
        UserTag currentUserTag = userUtils.getUserTag();
        List<UserFavor> userFavorList = currentUserTag.getFavors();
        List<UserFavorVo> userFavorVoList = new ArrayList<>();
        for (UserFavor favor : userFavorList) {
            userFavorVoList.add(favor.toUserFavorVo());
        }
        return userFavorVoList;
    }
}
