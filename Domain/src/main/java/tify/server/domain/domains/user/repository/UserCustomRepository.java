package tify.server.domain.domains.user.repository;


import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.dto.condition.NeighborCondition;
import tify.server.domain.domains.user.dto.condition.UserCondition;

public interface UserCustomRepository {

    Slice<User> searchUsers(Pageable pageable, UserCondition userCondition, Long currentUserId);

    List<User> findNeighbors(Long userId, NeighborCondition neighborCondition);
}
