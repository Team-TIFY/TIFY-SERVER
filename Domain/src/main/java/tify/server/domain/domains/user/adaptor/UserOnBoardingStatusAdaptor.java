package tify.server.domain.domains.user.adaptor;


import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.user.domain.UserOnBoardingStatus;
import tify.server.domain.domains.user.exception.UserOnBoardingStatusNotFoundException;
import tify.server.domain.domains.user.repository.UserOnBoardingStatusRepository;

@Adaptor
@RequiredArgsConstructor
public class UserOnBoardingStatusAdaptor {

    private final UserOnBoardingStatusRepository userOnBoardingStatusRepository;

    public UserOnBoardingStatus queryByName(String name) {
        return userOnBoardingStatusRepository
                .findByName(name)
                .orElseThrow(() -> UserOnBoardingStatusNotFoundException.EXCEPTION);
    }
}
