package tify.server.domain.domains.user.adaptor;


import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.user.domain.OauthInfo;
import tify.server.domain.domains.user.domain.UserResign;
import tify.server.domain.domains.user.exception.UserResignNotFoundException;
import tify.server.domain.domains.user.repository.UserResignRepository;

@Adaptor
@RequiredArgsConstructor
public class UserResignAdaptor {

    private final UserResignRepository userResignRepository;

    public UserResign query(Long id) {
        return userResignRepository
                .findById(id)
                .orElseThrow(() -> UserResignNotFoundException.EXCEPTION);
    }

    public List<UserResign> queryAll() {
        return userResignRepository.findAll();
    }

    public void save(UserResign userResign) {
        userResignRepository.save(userResign);
    }

    public boolean existByOauthInfo(OauthInfo oauthInfo) {
        return userResignRepository.existsByOauthInfo(oauthInfo);
    }

    public boolean existsByUserId(Long userId) {
        return userResignRepository.existsByUserId(userId);
    }

    public Optional<UserResign> optionalQueryByUserId(Long userId) {
        return userResignRepository.findByUserId(userId);
    }

    public UserResign queryByUserId(Long userId) {
        return userResignRepository
                .findByUserId(userId)
                .orElseThrow(() -> UserResignNotFoundException.EXCEPTION);
    }

    public Optional<UserResign> optionalQueryByOauthInfo(OauthInfo oauthInfo) {
        return userResignRepository.findByOauthInfo(oauthInfo);
    }

    public UserResign queryByOauthInfo(OauthInfo oauthInfo) {
        return userResignRepository
                .findByOauthInfo(oauthInfo)
                .orElseThrow(() -> UserResignNotFoundException.EXCEPTION);
    }

    public void delete(UserResign userResign) {
        userResignRepository.delete(userResign);
    }
}
