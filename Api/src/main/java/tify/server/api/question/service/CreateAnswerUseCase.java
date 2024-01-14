package tify.server.api.question.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import tify.server.api.alarm.model.dto.AnswerKnockEventDto;
import tify.server.api.config.security.SecurityUtils;
import tify.server.api.question.model.request.PostAnswerRequest;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.domain.Knock;
import tify.server.domain.domains.question.service.DailyQuestionDomainService;
import tify.server.domain.domains.question.validator.QuestionValidator;

@UseCase
@RequiredArgsConstructor
public class CreateAnswerUseCase {

    private final DailyQuestionDomainService dailyQuestionDomainService;
    private final QuestionValidator questionValidator;

    private final ApplicationEventPublisher applicationEventPublisher;

    public void execute(Long questionId, PostAnswerRequest body) {
        questionValidator.isValidDailyQuestion(questionId);
        List<Knock> knockList =
                dailyQuestionDomainService.createAnswer(
                        questionId, SecurityUtils.getCurrentUserId(), body.getAnswer());
        knockList.forEach(
                knock -> {
                    applicationEventPublisher.publishEvent(AnswerKnockEventDto.from(knock));
                });
    }
}
