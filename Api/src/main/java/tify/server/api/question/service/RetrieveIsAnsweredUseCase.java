package tify.server.api.question.service;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.api.question.model.response.RetrieveCategoryIsAnsweredDTO;
import tify.server.api.question.model.response.RetrieveIsAnsweredByDetailCategoryResponse;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.FavorAnswerAdaptor;
import tify.server.domain.domains.question.adaptor.FavorQuestionAdaptor;
import tify.server.domain.domains.question.domain.FavorQuestionCategory;
import tify.server.domain.domains.question.dto.model.FavorAnswerCategoryDto;
import tify.server.domain.domains.user.domain.DetailCategory;
import tify.server.domain.domains.user.domain.SmallCategory;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrieveIsAnsweredUseCase {

    private final FavorQuestionAdaptor favorQuestionAdaptor;
    private final FavorAnswerAdaptor favorAnswerAdaptor;

    public List<RetrieveCategoryIsAnsweredDTO> retrieveIsAnsweredBySmallCategory() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        List<RetrieveCategoryIsAnsweredDTO> categoryIsAnsweredDTOS = new ArrayList<>();
        List<SmallCategory> smallCategories = SmallCategory.getSmallCategories();

        List<FavorAnswerCategoryDto> favorAnswerCategoryDTOs =
                favorAnswerAdaptor.searchCategories(currentUserId);

        List<FavorQuestionCategory> favorQuestionCategories =
                favorQuestionAdaptor.queryAllFavorQuestionCategory();

        for (SmallCategory smallCategory : smallCategories) {
            int userAnswerCategorySize =
                    favorAnswerCategoryDTOs.stream()
                            .filter(dto -> dto.getSmallCategory().equals(smallCategory))
                            .toList()
                            .size();
            // smallCategory와 같은 smallCategory를 가지는 favorAnswerCategoryDTO의 개수

            int size =
                    favorQuestionCategories.stream()
                            .filter(category -> category.getSmallCategory().equals(smallCategory))
                            .toList()
                            .size();
            // smallCategory와 같은 smallCategory를 가지는 detailCategory의 개수

            categoryIsAnsweredDTOS.add(
                    RetrieveCategoryIsAnsweredDTO.of(
                            smallCategory, userAnswerCategorySize == size));
            // 두 size가 같으면 true, 아니면 false
        }

        return categoryIsAnsweredDTOS;
    }

    public List<RetrieveIsAnsweredByDetailCategoryResponse> retrieveIsAnsweredByDetailCategory(
            SmallCategory smallCategory) {

        Long currentUserId = SecurityUtils.getCurrentUserId();

        // smallCategory에 해당하는 detailCategory들 조회
        List<DetailCategory> detailCategories =
                DetailCategory.getDetailCategories().stream()
                        .filter(d -> d.getSmallCategory().equals(smallCategory))
                        .toList();

        return detailCategories.stream()
                .map(
                        detailCategory ->
                                new RetrieveIsAnsweredByDetailCategoryResponse(
                                        detailCategory,
                                        favorAnswerAdaptor.existsByDetailCategoryAndUserId(
                                                detailCategory, currentUserId)))
                .toList();
    }
}
