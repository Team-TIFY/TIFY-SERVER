package tify.server.domain.domains.user.adaptor;

import static tify.server.domain.domains.user.exception.UserException.ON_BOARDING_STATE_NOT_FOUND_ERROR;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import tify.server.core.annotation.Adaptor;
import tify.server.core.exception.BaseException;
import tify.server.domain.domains.user.domain.OauthInfo;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserOnBoardingStatus;
import tify.server.domain.domains.user.dto.condition.UserCondition;
import tify.server.domain.domains.user.exception.UserNotFoundException;
import tify.server.domain.domains.user.repository.UserOnBoardingStatusRepository;
import tify.server.domain.domains.user.repository.UserRepository;
import tify.server.domain.domains.user.vo.UserOnBoardingStatusInfoVo;

@Adaptor
@RequiredArgsConstructor
public class UserAdaptor {

    private final UserRepository userRepository;

    private final UserOnBoardingStatusRepository userOnBoardingStatusRepository;

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

    public UserOnBoardingStatus queryOnBoardingStatusByName(String name) {
        return userOnBoardingStatusRepository
                .findByName(name)
                .orElseThrow(() -> new BaseException(ON_BOARDING_STATE_NOT_FOUND_ERROR));
    }

    public List<UserOnBoardingStatusInfoVo> searchByKeyword(String keyword) {
        return userOnBoardingStatusRepository.searchByKeyword(keyword);
    }

    public Slice<User> searchUsers(Pageable pageable, UserCondition condition, Long currentUserId) {
        return userRepository.searchUsers(pageable, condition, currentUserId);
    }
}
