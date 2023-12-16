package tify.server.domain.domains.user.repository;

import static tify.server.domain.domains.user.domain.QUserFavor.userFavor;
import static tify.server.domain.domains.user.domain.QUserTag.userTag;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.domain.domains.user.domain.LargeCategory;
import tify.server.domain.domains.user.domain.UserFavor;
import tify.server.domain.domains.user.vo.UserFavorVo;

@RequiredArgsConstructor
public class UserFavorCustomRepositoryImpl implements UserFavorCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserFavorVo> findByLargeCategory(LargeCategory largeCategory) {
        List<UserFavor> favors =
                queryFactory
                        .select(userFavor)
                        .from(userFavor)
                        .join(userTag)
                        .on(userFavor.userTagId.eq(userTag.id))
                        .where(userTag.largeCategory.eq(largeCategory))
                        .orderBy(userFavor.createdAt.asc())
                        .fetch();

        return favors.stream().map(UserFavorVo::from).toList();
    }
}
