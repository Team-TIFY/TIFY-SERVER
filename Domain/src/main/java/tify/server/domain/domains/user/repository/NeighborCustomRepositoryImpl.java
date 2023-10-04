package tify.server.domain.domains.user.repository;

import static tify.server.domain.domains.QAbstractTimeStamp.*;
import static tify.server.domain.domains.user.domain.QNeighbor.neighbor;
import static tify.server.domain.domains.user.domain.QProfile.*;
import static tify.server.domain.domains.user.domain.QUser.*;
import static tify.server.domain.domains.user.domain.QUserOnBoardingStatus.*;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import tify.server.domain.common.util.SliceUtil;
import tify.server.domain.domains.user.dto.condition.NeighborCondition;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborDTO;

@RequiredArgsConstructor
public class NeighborCustomRepositoryImpl implements NeighborCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<RetrieveNeighborDTO> searchToPage(NeighborCondition neighborCondition) {
        List<RetrieveNeighborDTO> neighbors =
                queryFactory
                        .select(
                                Projections.constructor(
                                        RetrieveNeighborDTO.class,
                                        neighbor.toUserId,
                                        user.userId,
                                        user.profile.thumbNail,
                                        user.profile.userName,
                                        user.profile.birth,
                                        user.onBoardingStatus.name,
                                        neighbor.order,
                                        neighbor.isView,
                                        user.updatedAt))
                        .from(neighbor)
                        .join(user)
                        .on(user.id.eq(neighbor.toUserId))
                        .where(neighbor.fromUserId.eq(neighborCondition.getCurrentUserId()))
                        .orderBy(neighbor.order.asc())
                        .offset(neighborCondition.getPageable().getOffset())
                        .limit(neighborCondition.getPageable().getPageSize() + 1)
                        .fetch();

        return SliceUtil.valueOf(neighbors, neighborCondition.getPageable());
    }

    @Override
    public Slice<RetrieveNeighborDTO> searchBirthToPage(NeighborCondition neighborCondition) {
        LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        String monthAndYear =
                String.format("%02d%02d", today.getMonth().getValue(), today.getDayOfMonth());
        List<RetrieveNeighborDTO> neighbors =
                queryFactory
                        .select(
                                Projections.constructor(
                                        RetrieveNeighborDTO.class,
                                        neighbor.toUserId,
                                        user.userId,
                                        user.profile.thumbNail,
                                        user.profile.userName,
                                        user.profile.birth,
                                        user.onBoardingStatus.name,
                                        neighbor.order,
                                        neighbor.isView,
                                        user.updatedAt))
                        .from(neighbor)
                        .join(user)
                        .on(user.id.eq(neighbor.toUserId))
                        .where(
                                neighbor.fromUserId.eq(neighborCondition.getCurrentUserId()),
                                user.profile.birth.contains(monthAndYear))
                        .orderBy(neighbor.order.asc())
                        .offset(neighborCondition.getPageable().getOffset())
                        .limit(neighborCondition.getPageable().getPageSize() + 1)
                        .fetch();

        return SliceUtil.valueOf(neighbors, neighborCondition.getPageable());
    }
}
