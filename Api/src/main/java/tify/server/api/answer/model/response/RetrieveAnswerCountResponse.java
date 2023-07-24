package tify.server.api.answer.model.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RetrieveAnswerCountResponse {

    private final Long answerCount;

    public static RetrieveAnswerCountResponse of(Long answerCount) {
        return RetrieveAnswerCountResponse.builder().answerCount(answerCount).build();
    }
}
