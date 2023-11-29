package tify.server.api.answer.model.vo;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RetrieveAnswerCountVo {

    private final Long answerCount;

    public static RetrieveAnswerCountVo of(Long answerCount) {
        return RetrieveAnswerCountVo.builder().answerCount(answerCount).build();
    }
}
