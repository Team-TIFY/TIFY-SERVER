package tify.server.api.question.service;


import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.FavorQuestionAdaptor;

@UseCase
@RequiredArgsConstructor
public class RetrieveFavorQuestionUseCase {

    private final FavorQuestionAdaptor favorQuestionAdaptor;
}
