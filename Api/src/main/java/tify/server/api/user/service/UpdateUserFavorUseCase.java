package tify.server.api.user.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.user.model.dto.UserFavorDto;
import tify.server.api.user.model.dto.request.PatchUserFavorRequest;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.adaptor.UserFavorAdaptor;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserFavor;
import tify.server.domain.domains.user.exception.UserFavorUpdateException;

@UseCase
@RequiredArgsConstructor
public class UpdateUserFavorUseCase {

    private final UserAdaptor userAdaptor;
    private final UserFavorAdaptor userFavorAdaptor;

    @Transactional
    public void execute(Long userId, PatchUserFavorRequest body) {
        if (body.getUserFavorDtoList().size() != 3) {
            throw UserFavorUpdateException.EXCEPTION;
        }
        User user = userAdaptor.query(userId);
        List<UserFavor> userFavors = user.getUserFavors();
        userFavors.forEach(
                favor -> {
                    List<UserFavorDto> userFavorDtoList =
                            body.getUserFavorDtoList().stream()
                                    .filter(
                                            userFavorDto ->
                                                    userFavorDto
                                                            .getOrder()
                                                            .equals(favor.getOrder()))
                                    .toList();
                    favor.updateFavor(
                            userFavorDtoList.get(0).getDetailCategory(),
                            userFavorDtoList.get(0).getOrder());
                });
    }
}
