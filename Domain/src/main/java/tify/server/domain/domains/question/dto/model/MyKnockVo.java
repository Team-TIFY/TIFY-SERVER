package tify.server.domain.domains.question.dto.model;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import tify.server.domain.domains.question.domain.Knock;
import tify.server.domain.domains.user.domain.User;

@Getter
public class MyKnockVo {

    private User user;

    private Knock knock;

    @QueryProjection
    public MyKnockVo(User user, Knock knock) {
        this.user = user;
        this.knock = knock;
    }
}
