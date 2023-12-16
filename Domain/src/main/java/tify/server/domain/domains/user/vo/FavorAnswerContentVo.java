package tify.server.domain.domains.user.vo;


import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.DetailCategory;

@Getter
@Builder
public class FavorAnswerContentVo {

    private DetailCategory detailCategory;

    private Long number;

    private String answerContent;

    public static FavorAnswerContentVo of(
            DetailCategory detailCategory, Long number, String answerContent) {
        return FavorAnswerContentVo.builder()
                .detailCategory(detailCategory)
                .number(number)
                .answerContent(answerContent)
                .build();
    }
}
