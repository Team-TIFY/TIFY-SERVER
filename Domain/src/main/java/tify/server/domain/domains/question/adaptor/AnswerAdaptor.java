package tify.server.domain.domains.question.adaptor;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public List<List<DailyQuestionAnswerVo>> searchMyAnswer(
            Long userId, DailyQuestionCategory dailyQuestionCategory) {
        List<List<DailyQuestionAnswerVo>> list = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            list.add(answerRepository.searchMyAnswerToPage(userId, dailyQuestionCategory, i));
        }
        return list;
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
