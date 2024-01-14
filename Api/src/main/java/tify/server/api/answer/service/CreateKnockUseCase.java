package tify.server.api.answer.service;


import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.alarm.model.dto.CreateKnockEventDto;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.KnockAdaptor;
import tify.server.domain.domains.question.domain.Knock;
import tify.server.domain.domains.question.validator.QuestionValidator;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.validator.UserValidator;

@UseCase
@RequiredArgsConstructor
public class CreateKnockUseCase {

    private final UserAdaptor userAdaptor;
    private final UserValidator userValidator;
    private final QuestionValidator questionValidator;
    private final KnockAdaptor knockAdaptor;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void execute(Long questionId, Long userId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        userValidator.isValidUser(userId);
        userValidator.isResignedUser(userId); // 탈퇴한 유저인지 검증
        userValidator.isNeighbor(currentUserId, userId); // 친구인지를 검증
        questionValidator.isValidateAnswerToQuestion(
                questionId, userId); // 오늘 날짜의 질문이 맞는지, 친구가 이미 답을 남겼는지 검증
        Knock knock =
                Knock.builder()
                        .userId(currentUserId)
                        .knockedUserId(userId)
                        .dailyQuestionId(questionId)
                        .build();
        knockAdaptor.save(knock);
        applicationEventPublisher.publishEvent(CreateKnockEventDto.from(knock));
    }
}
