package tify.server.api.question.service;


import lombok.RequiredArgsConstructor;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.FavorAnswerAdaptor;
import tify.server.domain.domains.question.adaptor.FavorQuestionAdaptor;
import tify.server.domain.domains.question.domain.FavorQuestion;

@UseCase
@RequiredArgsConstructor
public class RetrieveAnswerOrNotUseCase {

    private final FavorQuestionAdaptor favorQuestionAdaptor;
    private final FavorAnswerAdaptor favorAnswerAdaptor;

    public boolean retrieveIsAnswered(Long favorQuestionId) {
        Long currentUser = SecurityUtils.getCurrentUserId();
        FavorQuestion favorQuestion = favorQuestionAdaptor.queryFavorQuestion(favorQuestionId);
        return favorAnswerAdaptor.existQueryByFavorQuestionAndUserId(favorQuestion, currentUser);
    }
}
