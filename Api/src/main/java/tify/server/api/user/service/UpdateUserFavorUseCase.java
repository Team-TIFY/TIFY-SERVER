package tify.server.api.user.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.user.model.dto.request.PatchUserFavorRequest;
import tify.server.core.annotation.UseCase;
import tify.server.core.exception.BaseException;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.adaptor.UserFavorAdaptor;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserFavor;
import tify.server.domain.domains.user.exception.UserException;

@UseCase
@RequiredArgsConstructor
public class UpdateUserFavorUseCase {

    private final UserAdaptor userAdaptor;
    private final UserFavorAdaptor userFavorAdaptor;

    @Transactional
    public void execute(Long userId, PatchUserFavorRequest body) {
        if (body.getUserFavorDtoList().size() != 3) {
            throw new BaseException(UserException.USER_FAVOR_UPDATE_ERROR);
        }
        User user = userAdaptor.query(userId);

        List<UserFavor> updateFavors =
            body.getUserFavorDtoList().stream()
                .map(
                    dto ->
                        UserFavor.builder()
                            .user(user)
                            .detailCategory(dto.getDetailCategory())
                            .build())
                .toList();
        user.updateUserFavors(updateFavors);
    }
}