package tify.server.domain.domains.question.repository;

import static tify.server.domain.domains.question.domain.QFavorAnswer.*;
import static tify.server.domain.domains.question.domain.QFavorQuestion.*;
import static tify.server.domain.domains.question.domain.QFavorQuestionCategory.*;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.domain.domains.question.dto.model.FavorAnswerCategoryDto;

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
                        .distinct()
                        .from(favorAnswer)
                        .join(favorQuestion)
                        .on(favorAnswer.favorQuestion.id.eq(favorQuestion.id))
                        .join(favorQuestionCategory)
                        .on(favorQuestion.id.eq(favorQuestionCategory.id))
                        .where(favorAnswer.userId.eq(currentUserId))
                        .fetch();

        return categoryDtos;
    }
}
