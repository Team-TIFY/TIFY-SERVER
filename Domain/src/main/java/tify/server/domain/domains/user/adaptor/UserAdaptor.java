package tify.server.domain.domains.user.adaptor;


import java.util.Optional;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.user.domain.OauthInfo;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.exception.UserNotFoundException;
import tify.server.domain.domains.user.repository.UserRepository;

@Adaptor
@RequiredArgsConstructor
public class UserAdaptor {

    private final UserRepository userRepository;

    public Boolean existByOauthInfo(OauthInfo oauthInfo) {
        return userRepository.findByOauthInfo(oauthInfo).isPresent();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User query(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    public Optional<User> queryByUserId(String userId) {
        return userRepository.findByUserId(userId);
    }
}
