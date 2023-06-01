package tify.server.domain.domains.user.adaptor;


import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.user.domain.Answer;
import tify.server.domain.domains.user.exception.AnswerNotFoundException;
import tify.server.domain.domains.user.repository.AnswerRepository;

@Adaptor
@RequiredArgsConstructor
public class AnswerAdaptor {

    private final AnswerRepository answerRepository;

    public Answer query(Long answerId) {
        return answerRepository
                .findById(answerId)
                .orElseThrow(() -> AnswerNotFoundException.EXCEPTION);
    }
}
