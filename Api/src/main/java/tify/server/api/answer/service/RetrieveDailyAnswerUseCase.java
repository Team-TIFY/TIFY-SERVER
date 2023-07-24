package tify.server.api.answer.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import tify.server.api.answer.mapper.AnswerMapper;
import tify.server.api.answer.model.response.RetrieveAnswerDTO;
import tify.server.api.common.slice.SliceResponse;
import tify.server.api.utils.UserUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.DailyQuestionAdaptor;
import tify.server.domain.domains.question.domain.DailyQuestion;
import tify.server.domain.domains.question.dto.condition.AnswerCondition;

@UseCase
@RequiredArgsConstructor
public class RetrieveDailyAnswerUseCase {

    private final AnswerMapper answerMapper;
    private final DailyQuestionAdaptor dailyQuestionAdaptor;
    private final UserUtils userUtils;

    public SliceResponse<RetrieveAnswerDTO> execute(Long questionId, Pageable pageable) {
        DailyQuestion dailyQuestion = dailyQuestionAdaptor.query(questionId);
        Long currentUserId = userUtils.getUserId();
        AnswerCondition answerCondition = new AnswerCondition(dailyQuestion.getId(), pageable);
        return answerMapper.toRetrieveAnswerListResponse(answerCondition, currentUserId);
    }
}
