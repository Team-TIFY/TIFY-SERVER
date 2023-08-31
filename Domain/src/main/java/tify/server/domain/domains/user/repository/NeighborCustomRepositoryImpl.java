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
import tify.server.domain.domains.user.dto.model.NeighborVo;

@RequiredArgsConstructor
public class NeighborCustomRepositoryImpl implements NeighborCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<NeighborVo> searchToPage(NeighborCondition neighborCondition) {
        List<NeighborVo> neighbors =
                queryFactory
                        .select(
                                Projections.constructor(
                                        NeighborVo.class, neighbor, profile, userOnBoardingStatus))
                        .from(user)
                        .join(neighbor)
                        .on(user.id.eq(neighbor.toUserId))
                        .where(user.id.in(neighborCondition.getNeighborIdList()))
                        .fetch();

        return SliceUtil.valueOf(neighbors, neighborCondition.getPageable());
    }
}
