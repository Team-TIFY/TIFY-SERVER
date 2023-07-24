package tify.server.domain.domains.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.core.annotation.DomainService;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.OauthInfo;
import tify.server.domain.domains.user.domain.Profile;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.exception.UserNotFoundException;
import tify.server.domain.domains.user.repository.UserRepository;
import tify.server.domain.domains.user.validator.UserValidator;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDomainService {

    private final UserAdaptor userAdaptor;
    private final UserValidator userValidator;
    private final UserRepository userRepository;

    @Transactional
    public User registerUser(Profile profile, OauthInfo oauthInfo) {
        userValidator.isNewUser(oauthInfo);

        User user = User.builder().profile(profile).oauthInfo(oauthInfo).build();
        return userAdaptor.save(user);
    }

    @Transactional
    public User registerUserForTest(Profile profile, OauthInfo oauthInfo) {
        return userRepository
                .findByOauthInfo(oauthInfo)
                .orElseGet(
                        () ->
                                userAdaptor.save(
                                        User.builder()
                                                .oauthInfo(oauthInfo)
                                                .profile(profile)
                                                .build()));
    }

    public User loginUser(OauthInfo oauthInfo) {
        return userRepository
                .findByOauthInfo(oauthInfo)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    public boolean userCanRegister(OauthInfo oauthInfo) {
        return userValidator.canRegister(oauthInfo);
    }
}
