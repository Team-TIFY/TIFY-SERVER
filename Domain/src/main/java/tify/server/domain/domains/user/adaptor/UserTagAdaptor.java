package tify.server.domain.domains.user.adaptor;


import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.user.domain.UserTag;
import tify.server.domain.domains.user.exception.UserTagNotFoundException;
import tify.server.domain.domains.user.repository.UserTagRepository;

@Adaptor
@RequiredArgsConstructor
public class UserTagAdaptor {

    private final UserTagRepository userTagRepository;

    public UserTag query(Long userTagId) {
        return userTagRepository
                .findById(userTagId)
                .orElseThrow(() -> UserTagNotFoundException.EXCEPTION);
    }
}
