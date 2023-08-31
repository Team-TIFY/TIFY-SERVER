package tify.server.domain.domains.question.repository;


import org.springframework.data.domain.Slice;
import tify.server.domain.domains.question.dto.condition.AnswerCondition;
import tify.server.domain.domains.question.dto.model.AnswerVo;

public interface AnswerCustomRepository {

    Slice<AnswerVo> searchToPage(Long userId, AnswerCondition answerCondition);

    Long countAnswer(Long questionId);
}
