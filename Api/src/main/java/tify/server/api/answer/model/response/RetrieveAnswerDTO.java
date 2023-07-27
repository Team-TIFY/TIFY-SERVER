package tify.server.api.answer.model.response;


import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import tify.server.api.answer.vo.AnswerInfoVo;
import tify.server.domain.domains.question.dto.model.AnswerVo;

@Getter
@Builder
public class RetrieveAnswerDTO {

    private final AnswerInfoVo answerInfo;

    private final Boolean isMine;

    public static RetrieveAnswerDTO of(AnswerVo answerVo, Long currentUserId) {
        return RetrieveAnswerDTO.builder()
                .answerInfo(AnswerInfoVo.from(answerVo))
                .isMine(Objects.equals(answerVo.getUser().getId(), currentUserId))
                .build();
    }
}
