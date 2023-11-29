package tify.server.domain.domains.question.repository;


import java.util.List;
import tify.server.domain.domains.question.domain.DailyQuestionCategory;
import tify.server.domain.domains.question.dto.condition.AnswerCondition;
import tify.server.domain.domains.question.dto.model.AnswerVo;
import tify.server.domain.domains.question.dto.model.DailyQuestionAnswerVo;

public interface AnswerCustomRepository {

    List<AnswerVo> searchAnswers(Long userId, AnswerCondition answerCondition);

    Long countAnswer(Long questionId);

    Long countMyAnswerByDailyQuestionCategory(Long userId, List<Long> dailyQuestionIdList);

    List<DailyQuestionAnswerVo> searchMyAnswerToPage(
            Long userId, DailyQuestionCategory dailyQuestionCategory, int month);
}
