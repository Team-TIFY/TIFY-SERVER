package tify.server.domain.domains.user.dto.condition;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
public class NeighborCondition {

    Long currentUserId;
    private Pageable pageable;
}
