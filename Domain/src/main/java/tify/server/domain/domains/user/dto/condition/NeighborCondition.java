package tify.server.domain.domains.user.dto.condition;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NeighborCondition {

    private Long currentUserId;
    private List<Long> blockedUserIdList;
    private List<Long> friendIdList;
}
