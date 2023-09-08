package tify.server.api.question.service;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.api.config.security.SecurityUtils;
import tify.server.api.question.model.response.RetrieveCategoryIsAnsweredDTO;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.FavorAnswerAdaptor;
import tify.server.domain.domains.question.adaptor.FavorQuestionAdaptor;
import tify.server.domain.domains.question.dto.model.FavorAnswerCategoryDto;
import tify.server.domain.domains.user.domain.DetailCategory;
import tify.server.domain.domains.user.domain.SmallCategory;

@UseCase
@RequiredArgsConstructor
public class RetrieveIsAnsweredUseCase {

    private final FavorQuestionAdaptor favorQuestionAdaptor;
    private final FavorAnswerAdaptor favorAnswerAdaptor;

    public List<RetrieveCategoryIsAnsweredDTO> retrieveIsAnswered() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        List<RetrieveCategoryIsAnsweredDTO> categoryIsAnsweredDTOS = new ArrayList<>();
        List<SmallCategory> smallCategories = SmallCategory.getSmallCategories();
        List<DetailCategory> detailCategories = DetailCategory.getDetailCategories();

        List<FavorAnswerCategoryDto> favorAnswerCategoryDTOs =
            favorAnswerAdaptor.searchCategories(currentUserId);

        for (SmallCategory smallCategory : smallCategories) {
            int userAnswerCategorySize =
                favorAnswerCategoryDTOs.stream()
                    .filter(dto -> dto.getSmallCategory().equals(smallCategory))
                    .toList()
                    .size(); // smallCategory와 같은 smallCategory를 가지는 favorAnswerCategoryDTO의
            // 개수
            int size =
                detailCategories.stream()
                    .filter(
                        detailCategory ->
                            detailCategory.getSmallCategory().equals(smallCategory))
                    .toList()
                    .size(); // smallCategory와 같은 smallCategory를 가지는 detailCategory의 개수
            categoryIsAnsweredDTOS.add(
                RetrieveCategoryIsAnsweredDTO.of(
                    smallCategory, userAnswerCategorySize == size));
            // 두 size가 같으면 true, 아니면 false
        }

        return categoryIsAnsweredDTOS;
    }
}
