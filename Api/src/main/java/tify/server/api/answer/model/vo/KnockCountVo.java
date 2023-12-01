package tify.server.api.answer.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.question.domain.Knock;

@Getter
@Builder
public class KnockCountVo {

    @Schema(description = "쿡 찌른 사람의 pk 값입니다.", example = "1(자신의 pk값)")
    private final Long fromUserId;

    @Schema(description = "쿡 찔러진 사람의 pk 값입니다.", example = "1(쿡 찔러진 친구의 pk값")
    private final Long knockedUserId;

    @Schema(description = "쿡 찌른 횟수입니다.", example = "20")
    private final int knockCount;

    public static KnockCountVo of(Knock knock, int count) {
        return KnockCountVo.builder()
                .fromUserId(knock.getUserId())
                .knockedUserId(knock.getKnockedUserId())
                .knockCount(count)
                .build();
    }
}
