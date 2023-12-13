package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserBlockAdaptor;
import tify.server.domain.domains.user.validator.UserValidator;

@UseCase
@RequiredArgsConstructor
public class UserBlockUseCase {

    private final UserBlockAdaptor userBlockAdaptor;
    private final UserValidator userValidator;
    private final NeighborAdaptor neighborAdaptor;

    @Transactional
    public void execute(Long toUserId) {
        Long userId = SecurityUtils.getCurrentUserId();

        // 원래 차단되어있는 유저인지 검증하고 차단
        userValidator.isNewBlock(userId, toUserId);
        userBlockAdaptor.save(userId, toUserId);

        // 타겟이 되는 유저와 친구 신청 내역이 있다면 신청을 모두 삭제
        neighborAdaptor
                .optionalQueryByFromUserIdAndToUserId(userId, toUserId)
                .ifPresent(neighborAdaptor::deleteNeighborApplication);
        neighborAdaptor
                .optionalQueryByFromUserIdAndToUserId(toUserId, userId)
                .ifPresent(neighborAdaptor::deleteNeighborApplication);

        // 타겟이 되는 유저와 원래 친구였다면 둘의 친구를 끊음 & 두 사람의 친구 목록 업데이트
        neighborAdaptor
                .queryByFromUserIdAndToUserId(userId, toUserId)
                .ifPresent(neighborAdaptor::delete);
        neighborAdaptor
                .queryByFromUserIdAndToUserId(toUserId, userId)
                .ifPresent(neighborAdaptor::delete);
    }

    @Transactional
    public void delete(Long toUserId) {
        Long userId = SecurityUtils.getCurrentUserId();
        userValidator.isBlocked(userId, toUserId);
        userBlockAdaptor.delete(userId, toUserId);
    }
}
