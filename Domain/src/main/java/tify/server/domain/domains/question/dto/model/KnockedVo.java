package tify.server.domain.domains.question.dto.model;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class KnockedVo {

    private String userId;

    private Long fromUserId;

    private Long toUserId;

    @QueryProjection
    public KnockedVo(String userId, Long fromUserId, Long toUserId) {
        this.userId = userId;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }
}
