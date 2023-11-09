package tify.server.domain.domains.question.repository;


import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import tify.server.domain.domains.question.dto.condition.AnswerCondition;
import tify.server.domain.domains.question.dto.model.AnswerVo;
import tify.server.domain.domains.question.dto.model.DailyQuestionAnswerVo;

public interface AnswerCustomRepository {

    Slice<AnswerVo> searchToPage(Long userId, AnswerCondition answerCondition);

    Long countAnswer(Long questionId);

    Long countMyAnswerByDailyQuestionCategory(Long userId, List<Long> dailyQuestionIdList);

    Slice<DailyQuestionAnswerVo> searchMyAnswerToPage(Long userId, Pageable pageable);
}
