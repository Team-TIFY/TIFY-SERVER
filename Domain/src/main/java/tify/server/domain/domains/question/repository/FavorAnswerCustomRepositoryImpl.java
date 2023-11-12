package tify.server.domain.domains.question.repository;

import static tify.server.domain.domains.question.domain.QFavorAnswer.*;
import static tify.server.domain.domains.question.domain.QFavorQuestion.*;
import static tify.server.domain.domains.question.domain.QFavorQuestionCategory.*;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.dto.model.FavorAnswerCategoryDto;
import tify.server.domain.domains.question.dto.model.FavorAnswerVo;
import tify.server.domain.domains.user.domain.SmallCategory;

@RequiredArgsConstructor
public class FavorAnswerCustomRepositoryImpl implements FavorAnswerCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<FavorAnswerCategoryDto> searchToAnswerCategory(Long currentUserId) {

        List<FavorAnswerCategoryDto> categoryDtos =
                queryFactory
                        .select(
                                Projections.constructor(
                                        FavorAnswerCategoryDto.class,
                                        favorQuestionCategory.smallCategory,
                                        favorQuestionCategory.detailCategory))
                        .from(favorAnswer)
                        .join(favorQuestion)
                        .on(favorAnswer.favorQuestion.id.eq(favorQuestion.id))
                        .join(favorQuestionCategory)
                        .on(favorQuestion.favorQuestionCategory.id.eq(favorQuestionCategory.id))
                        .where(favorAnswer.userId.eq(currentUserId))
                        .groupBy(favorQuestionCategory.detailCategory)
                        .fetch();

        return categoryDtos;
    }

    @Override
    public Optional<FavorAnswer> searchByCategoryAndNumber(
            Long userId, String category, Long number) {
        return Optional.ofNullable(
                queryFactory
                        .selectFrom(favorAnswer)
                        .innerJoin(favorAnswer.favorQuestion, favorQuestion)
                        .fetchJoin()
                        .innerJoin(favorQuestion.favorQuestionCategory, favorQuestionCategory)
                        .fetchJoin()
                        .where(
                                favorAnswer.userId.eq(userId),
                                favorQuestionCategory.name.eq(category),
                                favorQuestion.number.eq(number))
                        .fetchOne());
    }

    @Override
    public List<FavorAnswerVo> getFavorAnswerBySmallCategory(
            Long userId, List<SmallCategory> smallCategory) {
        return queryFactory
                .select(
                        Projections.constructor(
                                FavorAnswerVo.class,
                                favorAnswer.id,
                                favorQuestionCategory.largeCategory,
                                favorQuestionCategory.smallCategory,
                                favorAnswer.answerContent))
                .from(favorAnswer)
                .innerJoin(favorAnswer.favorQuestion, favorQuestion)
                .join(favorQuestionCategory)
                .on(favorQuestion.favorQuestionCategory.id.eq(favorQuestionCategory.id))
                .where(
                        favorQuestionCategory
                                .smallCategory
                                .in(smallCategory)
                                .and(favorAnswer.userId.eq(userId)))
                .fetch();
    }
}
