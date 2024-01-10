package tify.server.domain.domains.user.repository;

import static tify.server.domain.domains.user.domain.NeighborApplicationStatus.WAIT;
import static tify.server.domain.domains.user.domain.QNeighborApplication.neighborApplication;
import static tify.server.domain.domains.user.domain.QUser.user;
import static tify.server.domain.domains.user.domain.QUserResign.*;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import tify.server.domain.common.util.SliceUtil;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborApplicationDTO;

@RequiredArgsConstructor
public class NeighborApplicationCustomRepositoryImpl
        implements NeighborApplicationCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Slice<RetrieveNeighborApplicationDTO> searchAllNeighborApplicationByFromUserId(
            Pageable pageable, Long toUserId) {
        List<RetrieveNeighborApplicationDTO> contents =
                jpaQueryFactory
                        .select(
                                Projections.constructor(
                                        RetrieveNeighborApplicationDTO.class,
                                        neighborApplication.id,
                                        user,
                                        neighborApplication.neighborApplicationStatus))
                        .from(neighborApplication)
                        .innerJoin(user)
                        .on(user.id.eq(neighborApplication.fromUserId))
                        .leftJoin(userResign)
                        .on(user.id.eq(userResign.userId))
                        .where(
                                neighborApplication.toUserId.eq(toUserId),
                                neighborApplication.neighborApplicationStatus.eq(WAIT),
                                userResign.userId.isNull())
                        .orderBy(neighborApplication.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize() + 1)
                        .fetch();
        return SliceUtil.valueOf(contents, pageable);
    }
}
