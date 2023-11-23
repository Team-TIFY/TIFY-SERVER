package tify.server.domain.domains.question.adaptor;


import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.question.domain.Answer;
import tify.server.domain.domains.question.domain.DailyQuestion;
import tify.server.domain.domains.question.domain.DailyQuestionCategory;
import tify.server.domain.domains.question.dto.condition.AnswerCondition;
import tify.server.domain.domains.question.dto.model.AnswerVo;
import tify.server.domain.domains.question.dto.model.DailyQuestionAnswerVo;
import tify.server.domain.domains.question.exception.AnswerNotFoundException;
import tify.server.domain.domains.question.repository.AnswerRepository;
import tify.server.domain.domains.question.repository.DailyQuestionRepository;

@Adaptor
@RequiredArgsConstructor
public class AnswerAdaptor {

    private final DailyQuestionRepository dailyQuestionRepository;
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

    public Slice<AnswerVo> searchAnswer(Long userId, AnswerCondition answerCondition) {
        return answerRepository.searchToPage(userId, answerCondition);
    }

    public Slice<DailyQuestionAnswerVo> searchMyAnswer(
            Long userId, DailyQuestionCategory dailyQuestionCategory, Pageable pageable) {
        return answerRepository.searchMyAnswerToPage(userId, dailyQuestionCategory, pageable);
    }

    public Long queryUserAnswerCountByDailyQuestionCategory(
            Long userId, DailyQuestionCategory dailyQuestionCategory) {
        List<Long> dailyQuestionIdList =
                dailyQuestionRepository.findAllByCategory(dailyQuestionCategory).stream()
                        .map(DailyQuestion::getId)
                        .toList();
        return answerRepository.countMyAnswerByDailyQuestionCategory(userId, dailyQuestionIdList);
    }

    public Long countAllUserAnswer(Long userId) {
        return answerRepository.countAllByUserId(userId);
    }
}
