package tify.server.domain.domains.question.dto.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.LargeCategory;
import tify.server.domain.domains.user.domain.SmallCategory;

@Getter
@Builder
@AllArgsConstructor
public class FavorAnswerVo {

    private Long answerId;

    private LargeCategory largeCategory;

    private SmallCategory smallCategory;

    private String answer;
}
