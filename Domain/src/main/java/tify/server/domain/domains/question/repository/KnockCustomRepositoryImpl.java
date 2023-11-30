package tify.server.domain.domains.question.repository;

import static tify.server.domain.domains.question.domain.QKnock.knock;
import static tify.server.domain.domains.user.domain.QUser.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.domain.domains.question.dto.model.KnockedVo;
import tify.server.domain.domains.question.dto.model.MyKnockVo;

@RequiredArgsConstructor
public class KnockCustomRepositoryImpl implements KnockCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MyKnockVo> searchMyKnockList(Long questionId, Long userId) {
        return queryFactory
                .select(Projections.constructor(MyKnockVo.class, user, knock))
                .from(knock)
                .join(user)
                .on(knock.knockedUserId.eq(user.id))
                .where(knock.userId.eq(userId), knock.dailyQuestionId.eq(questionId))
                .fetch();
    }

    @Override
    public List<KnockedVo> searchKnockToMeList(Long questionId, Long userId) {
        return queryFactory
                .select(
                        Projections.constructor(
                                KnockedVo.class, user.userId, user.id, knock.knockedUserId))
                .from(knock)
                .join(user)
                .on(knock.userId.eq(user.id))
                .where(knock.knockedUserId.eq(userId), knock.dailyQuestionId.eq(questionId))
                .fetch();
    }
}
