package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import tify.server.api.user.model.dto.vo.MyDailyQuestionAnswerVo;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.AnswerAdaptor;
import tify.server.domain.domains.question.domain.DailyQuestionCategory;

@UseCase
@RequiredArgsConstructor
public class RetrieveMyDailyAnswerUseCase {

    private final AnswerAdaptor answerAdaptor;

    public Slice<MyDailyQuestionAnswerVo> execute(
            Long userId, DailyQuestionCategory dailyQuestionCategory, Pageable pageable) {
        return answerAdaptor
                .searchMyAnswer(userId, dailyQuestionCategory, pageable)
                .map(MyDailyQuestionAnswerVo::from);
    }

    public Long executeAllCount(Long userId) {
        return answerAdaptor.countAllUserAnswer(userId);
    }

    public Long executeCount(Long userId, DailyQuestionCategory dailyQuestionCategory) {
        return answerAdaptor.queryMyAnswerCountByDailyQuestionCategory(
                userId, dailyQuestionCategory);
    }
}
