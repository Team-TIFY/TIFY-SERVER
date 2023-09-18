package tify.server.api.question.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.domain.domains.user.domain.DetailCategory;

@Getter
@AllArgsConstructor
public class RetrieveIsAnsweredByDetailCategoryResponse {

    private final DetailCategory detailCategory;

    private final boolean isAnswered;
}
