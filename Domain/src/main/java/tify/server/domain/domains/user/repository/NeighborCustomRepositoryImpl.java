package tify.server.domain.domains.user.repository;

import static tify.server.domain.domains.user.domain.QNeighbor.neighbor;
import static tify.server.domain.domains.user.domain.QUser.user;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import tify.server.domain.common.util.SliceUtil;
import tify.server.domain.domains.user.dto.condition.NeighborCondition;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborDTO;

@RequiredArgsConstructor
public class NeighborCustomRepositoryImpl implements NeighborCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<RetrieveNeighborDTO> searchNeighbors(NeighborCondition neighborCondition) {
        return queryFactory
                .select(
                        Projections.constructor(
                                RetrieveNeighborDTO.class,
                                neighbor.toUserId,
                                neighbor.fromUserId,
                                user.profile.thumbNail,
                                user.profile.userName,
                                user.profile.birth,
                                user.onBoardingStatus.name,
                                neighbor.order,
                                neighbor.isView,
                                neighbor.isNew,
                                user.updatedAt,
                                neighbor.viewedAt))
                .from(neighbor)
                .join(user)
                .on(user.id.eq(neighbor.toUserId))
                .where(
                        neighbor.fromUserId.eq(neighborCondition.getCurrentUserId()),
                        neighbor.toUserId.notIn(neighborCondition.getBlockedUserIdList()),
                        neighbor.toUserId.in(neighborCondition.getFriendIdList()))
                .orderBy(neighbor.order.asc())
                .fetch();
    }

    @Override
    public List<RetrieveNeighborDTO> searchBirthdayNeighbors(NeighborCondition neighborCondition) {
        LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String monthAndYear =
                String.format("%02d%02d", today.getMonth().getValue(), today.getDayOfMonth());
        return queryFactory
                .select(
                        Projections.constructor(
                                RetrieveNeighborDTO.class,
                                neighbor.toUserId,
                                neighbor.fromUserId,
                                user.profile.thumbNail,
                                user.profile.userName,
                                user.profile.birth,
                                user.onBoardingStatus.name,
                                neighbor.order,
                                neighbor.isView,
                                neighbor.isNew,
                                user.updatedAt,
                                neighbor.viewedAt))
                .from(neighbor)
                .join(user)
                .on(user.id.eq(neighbor.toUserId))
                .where(
                        neighbor.fromUserId.eq(neighborCondition.getCurrentUserId()),
                        neighbor.toUserId.notIn(neighborCondition.getBlockedUserIdList()),
                        user.profile.birth.contains(monthAndYear),
                        neighbor.toUserId.in(neighborCondition.getFriendIdList()))
                .orderBy(neighbor.order.asc())
                .fetch();
    }

    @Override
    public Slice<RetrieveNeighborDTO> searchNeighborsToPage(
            NeighborCondition neighborCondition, Pageable pageable) {
        List<RetrieveNeighborDTO> retrieveNeighborDTOS =
                queryFactory
                        .select(
                                Projections.constructor(
                                        RetrieveNeighborDTO.class,
                                        neighbor.toUserId,
                                        neighbor.fromUserId,
                                        user.profile.thumbNail,
                                        user.profile.userName,
                                        user.profile.birth,
                                        user.onBoardingStatus.name,
                                        neighbor.order,
                                        neighbor.isView,
                                        neighbor.isNew,
                                        user.updatedAt,
                                        neighbor.viewedAt))
                        .from(neighbor)
                        .join(user)
                        .on(user.id.eq(neighbor.toUserId))
                        .where(
                                neighbor.fromUserId.eq(neighborCondition.getCurrentUserId()),
                                neighbor.toUserId.notIn(neighborCondition.getBlockedUserIdList()),
                                neighbor.toUserId.in(neighborCondition.getFriendIdList()))
                        .orderBy(neighbor.order.asc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();
        return SliceUtil.valueOf(retrieveNeighborDTOS, pageable);
    }
}
