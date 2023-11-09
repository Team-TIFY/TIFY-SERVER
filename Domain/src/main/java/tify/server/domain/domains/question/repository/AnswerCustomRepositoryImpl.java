package tify.server.domain.domains.question.repository;

import static tify.server.core.consts.Status.N;
import static tify.server.domain.domains.question.domain.QAnswer.answer;
import static tify.server.domain.domains.question.domain.QDailyQuestion.*;
import static tify.server.domain.domains.user.domain.QUser.user;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import tify.server.domain.common.util.SliceUtil;
import tify.server.domain.domains.question.dto.condition.AnswerCondition;
import tify.server.domain.domains.question.dto.model.AnswerVo;
import tify.server.domain.domains.question.dto.model.DailyQuestionAnswerVo;

@RequiredArgsConstructor
public class AnswerCustomRepositoryImpl implements AnswerCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<AnswerVo> searchToPage(Long userId, AnswerCondition answerCondition) {

        List<AnswerVo> answers =
                queryFactory
                        .select(Projections.constructor(AnswerVo.class, answer, user))
                        .from(answer)
                        .join(user)
                        .on(answer.userId.eq(user.id))
                        .where(
                                questionIdEq(answerCondition.getQuestionId()),
                                answer.isDeleted.eq(N),
                                answer.userId.in(answerCondition.getUserIdList()))
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

    @Override
    public Long countMyAnswerByDailyQuestionCategory(Long userId, List<Long> dailyQuestionIdList) {

        return queryFactory
                .select(answer.count())
                .from(answer)
                .where(answer.questionId.in(dailyQuestionIdList), answer.userId.eq(userId))
                .fetchOne();
    }

    @Override
    public Slice<DailyQuestionAnswerVo> searchMyAnswerToPage(Long userId, Pageable pageable) {

        List<DailyQuestionAnswerVo> dailyQuestionAnswerVoList =
                queryFactory
                        .select(
                                Projections.constructor(
                                        DailyQuestionAnswerVo.class, dailyQuestion, answer))
                        .from(answer)
                        .join(dailyQuestion)
                        .on(answer.questionId.eq(dailyQuestion.id))
                        .where(answer.userId.eq(userId))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();

        return SliceUtil.valueOf(dailyQuestionAnswerVoList, pageable);
    }
}
