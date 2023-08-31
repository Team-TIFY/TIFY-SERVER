package tify.server.domain.domains.question.repository;

import static tify.server.core.consts.Status.N;
import static tify.server.domain.domains.question.domain.QAnswer.answer;
import static tify.server.domain.domains.user.domain.QNeighbor.neighbor;
import static tify.server.domain.domains.user.domain.QUser.user;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import tify.server.domain.common.util.SliceUtil;
import tify.server.domain.domains.question.dto.condition.AnswerCondition;
import tify.server.domain.domains.question.dto.model.AnswerVo;

@RequiredArgsConstructor
public class AnswerCustomRepositoryImpl implements AnswerCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<AnswerVo> searchToPage(Long userId, AnswerCondition answerCondition) {

        List<AnswerVo> answers =
                queryFactory
                        .select(Projections.constructor(AnswerVo.class, answer, user))
                        .from(neighbor)
                        .join(answer)
                        .on(answer.userId.eq(neighbor.toUserId))
                        .join(user)
                        .on(user.id.eq(neighbor.toUserId))
                        .where(
                                neighbor.fromUserId.eq(userId),
                                neighbor.isView.eq(true),
                                questionIdEq(answerCondition.getQuestionId()),
                                answer.isDeleted.eq(N))
                        .orderBy(neighbor.order.asc())
                        .offset(answerCondition.getPageable().getOffset())
                        .limit(answerCondition.getPageable().getPageSize() + 1)
                        .fetch();

        return SliceUtil.valueOf(answers, answerCondition.getPageable());
    }

    private BooleanExpression questionIdEq(Long questionId) {
        return questionId == null ? null : answer.questionId.eq(questionId);
    }

    @Override
    public Long countAnswer(Long questionId) {

        return queryFactory
                .select(answer.count())
                .from(answer)
                .where(questionIdEq(questionId), answer.isDeleted.eq(N))
                .fetchOne();
    }
}
