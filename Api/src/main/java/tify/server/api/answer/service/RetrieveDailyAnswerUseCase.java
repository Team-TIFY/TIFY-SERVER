package tify.server.api.answer.service;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.answer.model.response.RetrieveAnswerDTO;
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
    public SliceResponse<RetrieveAnswerDTO> execute(Long questionId, Pageable pageable) {
        DailyQuestion dailyQuestion = dailyQuestionAdaptor.query(questionId);
        Long currentUserId = userUtils.getUserId();
        List<Long> neighbors =
                new ArrayList<>(
                        neighborAdaptor.queryAllByFromUserId(currentUserId).stream()
                                .map(Neighbor::getToUserId)
                                .toList());
        neighbors.add(currentUserId);
        AnswerCondition answerCondition =
                new AnswerCondition(dailyQuestion.getId(), neighbors, pageable);
        Slice<AnswerVo> answers = answerAdaptor.searchAnswer(answerCondition);
        return SliceResponse.of(answers.map(answer -> RetrieveAnswerDTO.of(answer, currentUserId)));
    }
}
