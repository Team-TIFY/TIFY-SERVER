package tify.server.api.question.service;


import lombok.RequiredArgsConstructor;
import tify.server.api.config.security.SecurityUtils;
import tify.server.api.question.model.request.PostAnswerRequest;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.service.DailyQuestionDomainService;

@UseCase
@RequiredArgsConstructor
public class CreateAnswerUseCase {

    private final DailyQuestionDomainService dailyQuestionDomainService;

    public void execute(Long questionId, PostAnswerRequest body) {
        dailyQuestionDomainService.createAnswer(
                questionId, SecurityUtils.getCurrentUserId(), body.getAnswer());
    }
}
