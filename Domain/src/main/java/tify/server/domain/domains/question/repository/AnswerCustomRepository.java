package tify.server.domain.domains.question.repository;


import org.springframework.data.domain.Slice;
import tify.server.domain.domains.question.domain.Answer;
import tify.server.domain.domains.question.dto.condition.AnswerCondition;

public interface AnswerCustomRepository {

    Slice<Answer> searchToPage(AnswerCondition answerCondition);

    Long countAnswer(Long questionId);
}
