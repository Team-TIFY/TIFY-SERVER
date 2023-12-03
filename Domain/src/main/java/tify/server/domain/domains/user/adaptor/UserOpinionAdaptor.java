package tify.server.domain.domains.user.adaptor;


import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserOpinion;
import tify.server.domain.domains.user.exception.UserOpinionNotFoundException;
import tify.server.domain.domains.user.repository.UserOpinionRepository;

@Adaptor
@RequiredArgsConstructor
public class UserOpinionAdaptor {

    private final UserOpinionRepository userOpinionRepository;

    public UserOpinion query(Long id) {
        return userOpinionRepository
                .findById(id)
                .orElseThrow(() -> UserOpinionNotFoundException.EXCEPTION);
    }

    public List<UserOpinion> queryAllByUser(User user) {
        return userOpinionRepository.findAllByUser(user);
    }

    public void save(UserOpinion userOpinion) {
        userOpinionRepository.save(userOpinion);
    }
}
