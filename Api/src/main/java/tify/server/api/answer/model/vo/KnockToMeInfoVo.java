package tify.server.api.answer.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.question.dto.model.KnockedVo;

@Getter
@Builder
public class KnockToMeInfoVo {

    @Schema(description = "쿡 찌른 사람의 pk 값입니다.", example = "1(나를 찌른 사람의 id값)")
    private final Long fromUserId;

    @Schema(description = "쿡 찔러진 사람의 pk 값입니다.", example = "1(자신의 id값)")
    private final Long knockedUserId;

    @Schema(description = "쿡 찌른 사람의 id입니다.", example = "@tify_friends")
    private final String neighborId;

    public static KnockToMeInfoVo from(KnockedVo knockedVo) {
        return KnockToMeInfoVo.builder()
                .fromUserId(knockedVo.getFromUserId())
                .knockedUserId(knockedVo.getToUserId())
                .neighborId(knockedVo.getUserId())
                .build();
    }
}
