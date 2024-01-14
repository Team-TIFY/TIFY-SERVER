package tify.server.api.alarm.model.condition;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import tify.server.core.consts.Status;

@Getter
@Builder
public class AlarmCondition {

    @Schema(description = "필터로 쓰일 유저의 pk값입니다.", example = "1")
    private final Long userId;

    @Schema(description = "필터로 쓰일 알림의 조회여부입니다.", implementation = Status.class)
    private final Status status;

    @Schema(description = "필터로 쓰일 알림의 제목입니다.", example = "답변 가능한 취향 질문이 남아있어요 \uD83D\uDC40")
    private final String title;
}
