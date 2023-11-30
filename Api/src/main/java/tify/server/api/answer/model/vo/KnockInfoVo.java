package tify.server.api.answer.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.question.dto.model.MyKnockVo;

@Getter
@Builder
public class KnockInfoVo {

    @Schema(description = "쿡 찌른 사람의 pk 값입니다.", example = "1(자신의 pk값)")
    private final Long fromUserId;

    @Schema(description = "쿡 찔러진 사람의 pk 값입니다.", example = "1(쿡 찔러진 친구의 pk값")
    private final Long knockedUserId;

    @Schema(description = "쿡 찔러진 사람의 id입니다.", example = "tify_friends")
    private final String neighborId;

    @Schema(description = "쿡 찔러진 사람의 프로필사진입니다.", example = "image url")
    private final String imageUrl;

    public static KnockInfoVo from(MyKnockVo myKnockVo) {
        return KnockInfoVo.builder()
                .fromUserId(myKnockVo.getKnock().getUserId())
                .knockedUserId(myKnockVo.getKnock().getKnockedUserId())
                .neighborId(myKnockVo.getUser().getUserId())
                .imageUrl(myKnockVo.getUser().getProfile().getThumbNail())
                .build();
    }
}
