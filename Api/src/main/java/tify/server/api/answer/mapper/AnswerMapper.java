package tify.server.api.answer.mapper;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.answer.model.response.RetrieveAnswerCountResponse;
import tify.server.api.answer.model.response.RetrieveAnswerDTO;
import tify.server.api.common.slice.SliceResponse;
import tify.server.core.annotation.Mapper;
import tify.server.domain.domains.question.adaptor.AnswerAdaptor;
import tify.server.domain.domains.question.domain.Answer;
import tify.server.domain.domains.question.dto.condition.AnswerCondition;

@Mapper
@RequiredArgsConstructor
public class AnswerMapper {

    private final AnswerAdaptor answerAdaptor;

    public SliceResponse<RetrieveAnswerDTO> toRetrieveAnswerListResponse(
            AnswerCondition answerCondition, Long currentUserId) {
        Slice<Answer> answers = answerAdaptor.searchAnswer(answerCondition);
        return SliceResponse.of(answers.map(answer -> toRetrieveAnswerDTO(answer, currentUserId)));
    }

    @Transactional(readOnly = true)
    public Answer retrieveAnswer(Long answerId) {
        return answerAdaptor.query(answerId);
    }

    public RetrieveAnswerCountResponse toRetrieveAnswerCountResponse(Long answerCount) {
        return RetrieveAnswerCountResponse.of(answerCount);
    }

    private RetrieveAnswerDTO toRetrieveAnswerDTO(Answer answer, Long currentUserId) {
        return RetrieveAnswerDTO.of(answer, currentUserId);
    }
}
