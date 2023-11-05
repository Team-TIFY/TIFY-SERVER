package tify.server.domain.domains.user.adaptor;


import java.util.Optional;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.user.domain.UserBlock;
import tify.server.domain.domains.user.exception.UserBlockNotFoundException;
import tify.server.domain.domains.user.repository.UserBlockRepository;

@Adaptor
@RequiredArgsConstructor
public class UserBlockAdaptor {

    private final UserBlockRepository userBlockRepository;

    public UserBlock query(Long id) {
        return userBlockRepository
                .findById(id)
                .orElseThrow(() -> UserBlockNotFoundException.EXCEPTION);
    }

    public boolean existsByFromUserIdAndToUserId(Long fromUserId, Long toUserId) {
        return userBlockRepository.existsByFromUserIdAndToUserId(fromUserId, toUserId);
    }

    public Optional<UserBlock> queryByFromUserIdAndToUserId(Long fromUserId, Long toUserId) {
        return userBlockRepository.findByFromUserIdAndToUserId(fromUserId, toUserId);
    }

    public void save(Long fromUserId, Long toUserId) {
        userBlockRepository.save(
                UserBlock.builder().fromUserId(fromUserId).toUserId(toUserId).build());
    }

    public void delete(Long fromUserId, Long toUserId) {
        UserBlock userBlock =
                userBlockRepository
                        .findByFromUserIdAndToUserId(fromUserId, toUserId)
                        .orElseThrow(() -> UserBlockNotFoundException.EXCEPTION);
        userBlockRepository.delete(userBlock);
    }
}
