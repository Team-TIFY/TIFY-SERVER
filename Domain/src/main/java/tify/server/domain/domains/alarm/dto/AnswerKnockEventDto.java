package tify.server.domain.domains.alarm.dto;


import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.question.domain.Knock;

@Getter
@Builder
public class AnswerKnockEventDto {

    private final Long fromUserId;

    private final Long toUserId;

    public static AnswerKnockEventDto from(Knock knock) {
        return AnswerKnockEventDto.builder()
                .fromUserId(knock.getUserId())
                .toUserId(knock.getKnockedUserId())
                .build();
    }
}
