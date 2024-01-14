package tify.server.api.alarm.model.dto;


import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.NeighborApplication;

@Getter
@Builder
public class ReceiveApplicationEventDto {

    private final Long fromUserId;

    private final Long toUserId;

    public static ReceiveApplicationEventDto from(NeighborApplication application) {
        return ReceiveApplicationEventDto.builder()
                .fromUserId(application.getFromUserId())
                .toUserId(application.getToUserId())
                .build();
    }
}
