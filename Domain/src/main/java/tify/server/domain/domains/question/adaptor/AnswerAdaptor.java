package tify.server.domain.domains.question.adaptor;


import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.question.domain.Answer;
import tify.server.domain.domains.question.dto.condition.AnswerCondition;
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

    public Optional<Answer> optionalQueryByQuestionAndUser(Long questionId, Long userId) {
        return answerRepository.findByQuestionIdAndUserId(questionId, userId);
    }
    
    public Long queryAnswerCount(Long questionId) {
        return answerRepository.countAnswer(questionId);
    }

    public Slice<Answer> searchAnswer(AnswerCondition answerCondition) {
        return answerRepository.searchToPage(answerCondition);
    }
}
