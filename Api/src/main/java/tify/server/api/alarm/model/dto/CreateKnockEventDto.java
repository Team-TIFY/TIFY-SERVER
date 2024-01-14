package tify.server.api.alarm.model.dto;


import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.question.domain.Knock;

@Getter
@Builder
public class CreateKnockEventDto {

    private final Long fromUserId;

    private final Long toUserId;

    private final Long questionId;

    public static CreateKnockEventDto from(Knock knock) {
        return CreateKnockEventDto.builder()
                .fromUserId(knock.getUserId())
                .toUserId(knock.getKnockedUserId())
                .questionId(knock.getDailyQuestionId())
                .build();
    }
}
