package tify.server.domain.domains.alarm.dto;


import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.NeighborApplication;

@Getter
@Builder
public class SendApplicationEventDto {

    private final Long fromUserId;

    private final Long toUserId;

    public static SendApplicationEventDto from(NeighborApplication application) {
        return SendApplicationEventDto.builder()
                .fromUserId(application.getFromUserId())
                .toUserId(application.getToUserId())
                .build();
    }
}
