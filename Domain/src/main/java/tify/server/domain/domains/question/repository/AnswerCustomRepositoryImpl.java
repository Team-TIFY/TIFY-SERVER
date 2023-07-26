package tify.server.domain.domains.question.repository;

import static tify.server.core.consts.Status.N;
import static tify.server.domain.domains.question.domain.QAnswer.answer;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import tify.server.domain.common.util.SliceUtil;
import tify.server.domain.domains.question.domain.Answer;
import tify.server.domain.domains.question.dto.condition.AnswerCondition;

@RequiredArgsConstructor
public class AnswerCustomRepositoryImpl implements AnswerCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Answer> searchToPage(AnswerCondition answerCondition) {
        List<Answer> answers =
                queryFactory
                        .selectFrom(answer)
                        .where(
                                questionIdEq(answerCondition.getQuestionId()),
                                answer.userId.in(answerCondition.getNeighbors()),
                                answer.isDeleted.eq(N))
                        .orderBy(answer.createdAt.desc())
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
