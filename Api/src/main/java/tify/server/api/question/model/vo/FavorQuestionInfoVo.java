package tify.server.api.question.model.vo;


import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.question.domain.FavorQuestion;

@Getter
@Builder
public class FavorQuestionInfoVo {

    private final Long favorQuestionId;

    private final String favorQuestionCategoryName;

    private final Long number;

    private final String contents;

    public static FavorQuestionInfoVo from(FavorQuestion favorQuestion) {
        return FavorQuestionInfoVo.builder()
                .favorQuestionId(favorQuestion.getId())
                .favorQuestionCategoryName(favorQuestion.getFavorQuestionCategory().getName())
                .number(favorQuestion.getNumber())
                .contents(favorQuestion.getQuestionContent())
                .build();
    }
}
