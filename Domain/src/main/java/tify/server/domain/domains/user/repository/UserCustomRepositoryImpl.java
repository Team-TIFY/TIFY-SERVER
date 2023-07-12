package tify.server.domain.domains.user.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    //  @Override
    //  public List<UserTagVo> findUserTagVo(Long userId) {
    //    return jpaQueryFactory.select(new QUserTagVo(userTag.id, largeCategory.value,
    // userTag.favors))
    //            .from(user).leftJoin(user.userTags, userTag).fetchJoin()
    //            .leftJoin(userTag.favors, userFavor).fetchJoin()
    //            .where(user.id.eq(userId)).fetch();
    //  }

}
