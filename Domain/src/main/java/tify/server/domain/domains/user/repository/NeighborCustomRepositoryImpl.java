package tify.server.domain.domains.user.repository;

import static tify.server.domain.domains.user.domain.QNeighbor.neighbor;
import static tify.server.domain.domains.user.domain.QProfile.*;
import static tify.server.domain.domains.user.domain.QUser.*;
import static tify.server.domain.domains.user.domain.QUserOnBoardingStatus.*;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
                                        user.onBoardingStatus.name))
                        .from(neighbor)
                        .join(user)
                        .on(user.id.eq(neighbor.toUserId))
                        .where(neighbor.fromUserId.eq(neighborCondition.getCurrentUserId()))
                        .offset(neighborCondition.getPageable().getOffset())
                        .limit(neighborCondition.getPageable().getPageSize())
                        .fetch();

        return SliceUtil.valueOf(neighbors, neighborCondition.getPageable());
    }
}
