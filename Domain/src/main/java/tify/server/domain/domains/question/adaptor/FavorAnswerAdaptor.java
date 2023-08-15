package tify.server.domain.domains.question.adaptor;


import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.exception.FavorAnswerNotFoundException;
import tify.server.domain.domains.question.repository.FavorAnswerRepository;

@Adaptor
@RequiredArgsConstructor
public class FavorAnswerAdaptor {

    private final FavorAnswerRepository favorAnswerRepository;

    public FavorAnswer query(Long favorAnswerId) {
        return favorAnswerRepository
                .findById(favorAnswerId)
                .orElseThrow(() -> FavorAnswerNotFoundException.EXCEPTION);
    }
}
