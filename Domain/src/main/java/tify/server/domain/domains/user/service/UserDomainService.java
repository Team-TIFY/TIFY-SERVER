package tify.server.domain.domains.user.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.core.annotation.DomainService;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.adaptor.UserResignAdaptor;
import tify.server.domain.domains.user.domain.OauthInfo;
import tify.server.domain.domains.user.domain.Profile;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserResign;
import tify.server.domain.domains.user.exception.UserNotFoundException;
import tify.server.domain.domains.user.repository.UserRepository;
import tify.server.domain.domains.user.validator.UserValidator;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDomainService {

    private final UserAdaptor userAdaptor;
    private final UserResignAdaptor userResignAdaptor;
    private final UserValidator userValidator;
    private final UserRepository userRepository;

    @Transactional
    public User registerUser(OauthInfo oauthInfo) {
        if (userResignAdaptor.optionalQueryByOauthInfo(oauthInfo).isPresent()) {
            // 유저가 탈퇴했는데 다시 가입하려고 할 시
            UserResign userResign = userResignAdaptor.queryByOauthInfo(oauthInfo);
            userResignAdaptor.delete(userResign);
            return userAdaptor.queryByOauthInfo(oauthInfo);
        } else {
            // 일반 회원가입
            userValidator.isNewUser(oauthInfo);
            User user = User.builder().oauthInfo(oauthInfo).build();
            return userAdaptor.save(user);
        }
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
        userValidator.isResignedUser(oauthInfo);
        return userRepository
                .findByOauthInfo(oauthInfo)
                .orElseThrow(() -> UserNotFoundException.EXCEPTION);
    }

    public boolean userCanRegister(OauthInfo oauthInfo) {
        return !userAdaptor.existByOauthInfo(oauthInfo)
                || userResignAdaptor.existByOauthInfo(oauthInfo);
    }

    public List<User> getResignedUsers() {
        return userResignAdaptor.queryAll().stream().map(resign -> {
            LocalDateTime resignedTime = resign.getCreatedAt().toLocalDateTime()
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
            if (resignedTime.isEqual(LocalDateTime.now().minusMonths(6))) {
                return userAdaptor.query(resign.getUserId());
            }
            return null;
        }).filter(Objects::nonNull).toList();
    }
}
