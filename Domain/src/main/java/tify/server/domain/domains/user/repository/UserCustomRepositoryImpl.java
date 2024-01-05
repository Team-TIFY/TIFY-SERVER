package tify.server.domain.domains.user.repository;

import static tify.server.domain.domains.user.domain.QNeighbor.*;
import static tify.server.domain.domains.user.domain.QUser.user;
import static tify.server.domain.domains.user.domain.QUserBlock.userBlock;
import static tify.server.domain.domains.user.domain.QUserOnBoardingStatus.*;
import static tify.server.domain.domains.user.domain.QUserResign.*;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import tify.server.domain.common.util.SliceUtil;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.dto.condition.UserCondition;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborFavorBoxDTO;

@RequiredArgsConstructor
public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<User> searchUsers(
            Pageable pageable, UserCondition userCondition, Long currentUserId) {
        List<User> contents =
                jpaQueryFactory
                        .selectFrom(user)
                        .leftJoin(userBlock)
                        .on(user.id.eq(userBlock.fromUserId), userBlock.toUserId.eq(currentUserId))
                        .leftJoin(userResign)
                        .on(user.id.eq(userResign.userId))
                        .where(
                                userBlock.fromUserId.isNull(),
                                userIdEquals(userCondition.getUserId()),
                                userResign.userId.isNull())
                        .orderBy(user.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();

        return SliceUtil.valueOf(contents, pageable);
    }

    @Override
    public List<RetrieveNeighborFavorBoxDTO> findNeighborsFavorBox(Long userId) {
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                RetrieveNeighborFavorBoxDTO.class, user, neighbor.viewedAt))
                .from(user)
                .join(neighbor)
                .on(user.id.eq(neighbor.toUserId))
                .leftJoin(userResign)
                .on(user.id.eq(userResign.userId))
                .where(neighbor.fromUserId.eq(userId), userResign.userId.isNull())
                .orderBy(neighbor.order.asc())
                .fetch();
    }

    private BooleanExpression userIdEquals(String userId) {
        return (userId != null) ? user.userId.eq(userId) : null;
    }
}
