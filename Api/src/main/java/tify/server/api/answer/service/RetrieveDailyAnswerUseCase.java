package tify.server.api.answer.service;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.answer.model.vo.RetrieveAnswerVo;
import tify.server.api.common.slice.SliceResponse;
import tify.server.api.utils.UserUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.AnswerAdaptor;
import tify.server.domain.domains.question.adaptor.DailyQuestionAdaptor;
import tify.server.domain.domains.question.domain.DailyQuestion;
import tify.server.domain.domains.question.dto.condition.AnswerCondition;
import tify.server.domain.domains.question.dto.model.AnswerVo;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.domain.Neighbor;

@UseCase
@RequiredArgsConstructor
public class RetrieveDailyAnswerUseCase {

    private final AnswerAdaptor answerAdaptor;
    private final NeighborAdaptor neighborAdaptor;
    private final DailyQuestionAdaptor dailyQuestionAdaptor;
    private final UserUtils userUtils;

    @Transactional(readOnly = true)
    public SliceResponse<RetrieveAnswerVo> execute(Long questionId, Pageable pageable) {
        DailyQuestion dailyQuestion = dailyQuestionAdaptor.query(questionId);
        Long currentUserId = userUtils.getUserId();
        List<Long> userIdList =
                new ArrayList<>(
                        neighborAdaptor.queryAllByFromUserIdAndIsView(currentUserId, true).stream()
                                .map(Neighbor::getToUserId)
                                .toList());
        userIdList.add(currentUserId);
        AnswerCondition answerCondition =
                new AnswerCondition(dailyQuestion.getId(), userIdList, pageable);
        Slice<AnswerVo> answers = answerAdaptor.searchAnswer(currentUserId, answerCondition);
        return SliceResponse.of(
                answers.map(answerVo -> RetrieveAnswerVo.of(answerVo, currentUserId)));
    }
}
