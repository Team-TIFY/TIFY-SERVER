package tify.server.domain.domains.question.adaptor;


import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.question.domain.Answer;
import tify.server.domain.domains.question.exception.AnswerNotFoundException;
import tify.server.domain.domains.question.repository.AnswerRepository;

@Adaptor
@RequiredArgsConstructor
public class AnswerAdaptor {

    private final AnswerRepository answerRepository;

    public Answer query(Long answerId) {
        return answerRepository
                .findById(answerId)
                .orElseThrow(() -> AnswerNotFoundException.EXCEPTION);
    }

    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }
}
