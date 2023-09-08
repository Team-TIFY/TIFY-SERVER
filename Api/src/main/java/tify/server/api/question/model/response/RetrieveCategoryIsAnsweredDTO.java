package tify.server.api.question.model.response;


import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.SmallCategory;

@Getter
@Builder
public class RetrieveCategoryIsAnsweredDTO {

    private final SmallCategory smallCategory;

    private final Boolean isAnswered;

    public static RetrieveCategoryIsAnsweredDTO of(
            SmallCategory smallCategory, Boolean isAnswered) {
        return RetrieveCategoryIsAnsweredDTO.builder()
                .smallCategory(smallCategory)
                .isAnswered(isAnswered)
                .build();
    }
}
