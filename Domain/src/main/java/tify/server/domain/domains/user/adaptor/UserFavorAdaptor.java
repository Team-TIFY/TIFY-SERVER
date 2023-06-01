package tify.server.domain.domains.user.adaptor;


import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.user.domain.UserFavor;
import tify.server.domain.domains.user.exception.UserFavorNotFoundException;
import tify.server.domain.domains.user.repository.UserFavorRepository;

@Adaptor
@RequiredArgsConstructor
public class UserFavorAdaptor {

    private final UserFavorRepository userFavorRepository;

    public UserFavor query(Long userFavorId) {
        return userFavorRepository
                .findById(userFavorId)
                .orElseThrow(() -> UserFavorNotFoundException.EXCEPTION);
    }
}
