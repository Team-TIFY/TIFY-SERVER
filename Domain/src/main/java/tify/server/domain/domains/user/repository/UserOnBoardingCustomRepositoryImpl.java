package tify.server.domain.domains.user.repository;

import static tify.server.domain.domains.user.domain.QUserOnBoardingStatus.userOnBoardingStatus;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.domain.domains.user.vo.QUserOnBoardingStatusInfoVo;
import tify.server.domain.domains.user.vo.UserOnBoardingStatusInfoVo;

@RequiredArgsConstructor
public class UserOnBoardingCustomRepositoryImpl implements UserOnBoardingCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<UserOnBoardingStatusInfoVo> searchByKeyword(String keyword) {
        return jpaQueryFactory
                .select(
                        new QUserOnBoardingStatusInfoVo(
                                userOnBoardingStatus.id, userOnBoardingStatus.name))
                .from(userOnBoardingStatus)
                .where(keywordContains(keyword))
                .fetch();
    }

    private BooleanExpression keywordContains(String keyword) {
        return (keyword != null) ? userOnBoardingStatus.keyword.contains(keyword) : null;
    }
}
