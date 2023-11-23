package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import tify.server.api.config.security.SecurityUtils;
import tify.server.api.user.model.dto.vo.MyDailyQuestionAnswerVo;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.AnswerAdaptor;
import tify.server.domain.domains.question.domain.DailyQuestionCategory;

@UseCase
@RequiredArgsConstructor
public class RetrieveMyDailyAnswerUseCase {

    private final AnswerAdaptor answerAdaptor;

    public Slice<MyDailyQuestionAnswerVo> execute(
            DailyQuestionCategory dailyQuestionCategory, Pageable pageable) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return answerAdaptor
                .searchMyAnswer(currentUserId, dailyQuestionCategory, pageable)
                .map(MyDailyQuestionAnswerVo::from);
    }

    public Long executeAllCount() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return answerAdaptor.countAllUserAnswer(currentUserId);
    }

    public Long executeCount(DailyQuestionCategory dailyQuestionCategory) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return answerAdaptor.queryMyAnswerCountByDailyQuestionCategory(
                currentUserId, dailyQuestionCategory);
    }
}
