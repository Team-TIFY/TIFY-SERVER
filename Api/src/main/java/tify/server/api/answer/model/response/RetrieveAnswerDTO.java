package tify.server.api.answer.model.response;


import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import tify.server.api.answer.vo.AnswerInfoVo;
import tify.server.domain.domains.question.domain.Answer;

@Getter
@Builder
public class RetrieveAnswerDTO {

    private final AnswerInfoVo answerInfo;

    private final Boolean isMine;

    public static RetrieveAnswerDTO of(Answer answer, Long currentUserId) {
        return RetrieveAnswerDTO.builder()
                .answerInfo(AnswerInfoVo.from(answer))
                .isMine(Objects.equals(answer.getUserId(), currentUserId))
                .build();
    }
}
