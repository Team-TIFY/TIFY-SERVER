package tify.server.domain.domains.user.vo;


import java.util.List;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.SmallCategory;

@Getter
@Builder
public class UserAnswerVo {

    private final SmallCategory smallCategory;

    private final List<String> answerContentList;

    public static UserAnswerVo of(SmallCategory smallCategory, List<String> answerContentList) {
        return UserAnswerVo.builder()
                .smallCategory(smallCategory)
                .answerContentList(answerContentList)
                .build();
    }
}
