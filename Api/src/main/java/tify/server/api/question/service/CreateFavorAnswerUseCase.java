package tify.server.api.question.service;


import lombok.RequiredArgsConstructor;
import tify.server.api.config.security.SecurityUtils;
import tify.server.api.question.model.request.PostFavorAnswerRequest;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.service.FavorQuestionDomainService;

@UseCase
@RequiredArgsConstructor
public class CreateFavorAnswerUseCase {

    private final FavorQuestionDomainService favorQuestionDomainService;

    public void execute(PostFavorAnswerRequest body) {
        favorQuestionDomainService.createFavorAnswer(
                SecurityUtils.getCurrentUserId(),
                body.getCategoryName(),
                body.getFavorAnswerDtos());
    }
}
