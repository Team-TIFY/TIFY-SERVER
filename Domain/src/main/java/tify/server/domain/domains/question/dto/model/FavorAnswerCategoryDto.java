package tify.server.domain.domains.question.dto.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.domain.domains.user.domain.DetailCategory;
import tify.server.domain.domains.user.domain.SmallCategory;

@Getter
@AllArgsConstructor
public class FavorAnswerCategoryDto {

    private final SmallCategory smallCategory;
    private final DetailCategory detailCategory;
}
