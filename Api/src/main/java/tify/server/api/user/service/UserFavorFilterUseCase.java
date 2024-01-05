package tify.server.api.user.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.user.model.dto.vo.UserFavorBoxVo;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.adaptor.UserFavorAdaptor;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.validator.UserValidator;
import tify.server.domain.domains.user.vo.UserFavorVo;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFavorFilterUseCase {

    private final UserAdaptor userAdaptor;
    private final UserFavorAdaptor userFavorAdaptor;
    private final UserValidator userValidator;

    @Transactional(readOnly = true)
    public List<UserFavorBoxVo> execute(Long userId) {
        userValidator.isResignedUser(userId);
        User user = userAdaptor.query(userId);
        return userFavorAdaptor.queryAllByUser(user).stream()
                .map(UserFavorVo::from)
                .map(UserFavorBoxVo::from)
                .toList();
    }
}
