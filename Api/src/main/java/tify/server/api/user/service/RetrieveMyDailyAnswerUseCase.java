package tify.server.api.user.service;


import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.api.user.model.dto.vo.MyDailyQuestionAnswerVo;
import tify.server.api.user.model.dto.vo.UserDailyQuestionAnswerVo;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.AnswerAdaptor;
import tify.server.domain.domains.question.domain.DailyQuestionCategory;

@UseCase
@RequiredArgsConstructor
public class RetrieveMyDailyAnswerUseCase {

    private final AnswerAdaptor answerAdaptor;

    public List<List<MyDailyQuestionAnswerVo>> execute(
            Long userId, DailyQuestionCategory dailyQuestionCategory) {
        return answerAdaptor.searchMyAnswer(userId, dailyQuestionCategory).stream()
                .map(list -> list.stream().map(MyDailyQuestionAnswerVo::from).toList())
                .toList();
    }

    public List<UserDailyQuestionAnswerVo> countByAllCategory(Long userId) {
        List<DailyQuestionCategory> dailyQuestionCategories =
                Arrays.stream(DailyQuestionCategory.values()).toList();
        return dailyQuestionCategories.stream()
                .map(
                        dailyQuestionCategory -> {
                            Long count =
                                    answerAdaptor.queryUserAnswerCountByDailyQuestionCategory(
                                            userId, dailyQuestionCategory);
                            return UserDailyQuestionAnswerVo.builder()
                                    .dailyQuestionCategory(dailyQuestionCategory)
                                    .count(count)
                                    .build();
                        })
                .toList();
    }

    public Long countByCategory(Long userId, DailyQuestionCategory dailyQuestionCategory) {
        return answerAdaptor.queryUserAnswerCountByDailyQuestionCategory(
                userId, dailyQuestionCategory);
    }
}
