package tify.server.domain.domains.question.repository;

import static tify.server.core.consts.Status.N;
import static tify.server.domain.domains.question.domain.QAnswer.answer;
import static tify.server.domain.domains.question.domain.QDailyQuestion.*;
import static tify.server.domain.domains.user.domain.QUser.user;
import static tify.server.domain.domains.user.domain.QUserResign.*;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.domain.domains.question.domain.DailyQuestionCategory;
import tify.server.domain.domains.question.dto.condition.AnswerCondition;
import tify.server.domain.domains.question.dto.model.AnswerVo;
import tify.server.domain.domains.question.dto.model.DailyQuestionAnswerVo;

@RequiredArgsConstructor
public class AnswerCustomRepositoryImpl implements AnswerCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<AnswerVo> searchAnswers(Long userId, AnswerCondition answerCondition) {

        return queryFactory
                .select(Projections.constructor(AnswerVo.class, answer, user))
                .from(answer)
                .join(user)
                .on(answer.userId.eq(user.id))
                .where(
                        questionIdEq(answerCondition.getQuestionId()),
                        answer.isDeleted.eq(N),
                        answer.userId.in(answerCondition.getUserIdList()))
                .fetch();
    }

    private BooleanExpression questionIdEq(Long questionId) {
        return questionId == null ? null : answer.questionId.eq(questionId);
    }

    @Override
    public Long countAnswer(Long questionId) {

        return queryFactory
                .select(answer.count())
                .from(answer)
                .leftJoin(userResign)
                .on(answer.userId.eq(userResign.userId))
                .where(questionIdEq(questionId), answer.isDeleted.eq(N), userResign.userId.isNull())
                .fetchOne();
    }

    @Override
    public Long countAnswersByDailyQuestionCategory(Long userId, List<Long> dailyQuestionIdList) {

        return queryFactory
                .select(answer.count())
                .from(answer)
                .leftJoin(userResign)
                .on(answer.userId.eq(userResign.userId))
                .where(
                        answer.questionId.in(dailyQuestionIdList),
                        answer.userId.eq(userId),
                        userResign.userId.isNull())
                .fetchOne();
    }

    @Override
    public List<DailyQuestionAnswerVo> searchMyAnswerToPage(
            Long userId, DailyQuestionCategory dailyQuestionCategory, int month) {

        return queryFactory
                .select(
                        Projections.constructor(
                                DailyQuestionAnswerVo.class,
                                answer.createdAt.month(),
                                dailyQuestion,
                                answer))
                .from(answer)
                .join(dailyQuestion)
                .on(answer.questionId.eq(dailyQuestion.id))
                .where(
                        answer.userId.eq(userId),
                        dailyQuestion.category.eq(dailyQuestionCategory),
                        answer.createdAt.month().eq(month))
                .orderBy(answer.createdAt.desc())
                .fetch();
    }
}
