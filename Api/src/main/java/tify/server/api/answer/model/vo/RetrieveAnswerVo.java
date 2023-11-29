package tify.server.api.answer.model.vo;


import java.util.Objects;
import lombok.Builder;
import lombok.Getter;
import tify.server.api.answer.model.vo.AnswerInfoVo;
import tify.server.domain.domains.question.dto.model.AnswerVo;

@Getter
@Builder
public class RetrieveAnswerVo {

    private final AnswerInfoVo answerInfo;

    private final Boolean isMine;

    public static RetrieveAnswerVo of(AnswerVo answerVo, Long currentUserId) {
        return RetrieveAnswerVo.builder()
                .answerInfo(AnswerInfoVo.from(answerVo))
                .isMine(Objects.equals(answerVo.getUser().getId(), currentUserId))
                .build();
    }
}
