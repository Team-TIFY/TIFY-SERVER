package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.alarm.dto.SendApplicationEventDto;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.NeighborApplication;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.exception.AlreadyExistNeighborRelationshipException;
import tify.server.domain.domains.user.exception.SelfNeighborException;
import tify.server.domain.domains.user.validator.UserValidator;

@UseCase
@RequiredArgsConstructor
@Transactional
public class CreateNeighborUseCase {

    private final UserAdaptor userAdaptor;
    private final NeighborAdaptor neighborAdaptor;
    private final UserValidator userValidator;

    private final ApplicationEventPublisher applicationEventPublisher;

    public void execute(Long toUserId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();

        if (currentUserId.equals(toUserId)) {
            throw SelfNeighborException.EXCEPTION;
        }

        userValidator.isValidUser(toUserId);
        User toUser = userAdaptor.query(toUserId);

        if (neighborAdaptor.existsNeighbor(currentUserId, toUser.getId())) {
            throw AlreadyExistNeighborRelationshipException.EXCEPTION;
        }

        // 반대편에서 이미 친구가 되어있다면 신청을 보내지 않음
        if (!neighborAdaptor
                .queryByFromUserIdAndToUserId(toUser.getId(), currentUserId)
                .isPresent()) {
            NeighborApplication application =
                    NeighborApplication.builder()
                            .fromUserId(SecurityUtils.getCurrentUserId())
                            .toUserId(toUserId)
                            .build();
            neighborAdaptor.saveNeighborApplication(application);
            applicationEventPublisher.publishEvent(SendApplicationEventDto.from(application));
        }
    }
}
