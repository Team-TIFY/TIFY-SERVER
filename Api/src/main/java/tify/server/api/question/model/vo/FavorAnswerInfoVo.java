package tify.server.api.question.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.question.dto.model.FavorAnswerVo;
import tify.server.domain.domains.user.domain.LargeCategory;
import tify.server.domain.domains.user.domain.SmallCategory;

@Getter
@Builder
public class FavorAnswerInfoVo {

    @Schema(description = "취향 답변의 id", example = "1")
    private Long answerId;

    @Schema(description = "취향 답변의 대분류", example = "BEAUTY")
    private LargeCategory largeCategory;

    @Schema(description = "취향 답변의 소분류", example = "FRAGRANCE")
    private SmallCategory smallCategory;

    @Schema(description = "취향 답변 내용", example = "페리페라")
    private String answer;

    public static FavorAnswerInfoVo from(FavorAnswerVo favorAnswerVo) {
        return FavorAnswerInfoVo.builder()
                .answerId(favorAnswerVo.getAnswerId())
                .largeCategory(favorAnswerVo.getLargeCategory())
                .smallCategory(favorAnswerVo.getSmallCategory())
                .answer(favorAnswerVo.getAnswer())
                .build();
    }
}
