package tify.server.api.answer.service;


import lombok.RequiredArgsConstructor;
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

    public void execute(Long questionId, Long userId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        userValidator.isNeighbor(currentUserId, userId); // 친구인지를 검증
        questionValidator.isValidateAnswerToQuestion(
                questionId, userId); // 오늘 날짜의 질문이 맞는지, 친구가 이미 답을 남겼는지 검증
        knockAdaptor.save(
                Knock.builder()
                        .userId(currentUserId)
                        .knockedUserId(userId)
                        .dailyQuestionId(questionId)
                        .build());
    }
}
