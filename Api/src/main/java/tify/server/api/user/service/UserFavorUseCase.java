package tify.server.api.user.service;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.FavorAnswerAdaptor;
import tify.server.domain.domains.question.adaptor.FavorQuestionAdaptor;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.domain.FavorQuestionCategory;
import tify.server.domain.domains.question.dto.model.FavorAnswerCategoryDto;
import tify.server.domain.domains.user.domain.DetailCategory;
import tify.server.domain.domains.user.domain.SmallCategory;
import tify.server.domain.domains.user.vo.FavorAnswerContentVo;
import tify.server.domain.domains.user.vo.UserAnswerVo;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFavorUseCase {

    private final FavorAnswerAdaptor favorAnswerAdaptor;
    private final FavorQuestionAdaptor favorQuestionAdaptor;

    public List<UserAnswerVo> execute(Long userId, List<SmallCategory> smallCategoryList) {
        List<FavorAnswerCategoryDto> favorAnswerCategoryDTOs =
                favorAnswerAdaptor.searchCategories(userId);

        List<FavorQuestionCategory> favorQuestionCategories =
                favorQuestionAdaptor.queryAllFavorQuestionCategory();

        // TODO : 리팩토링 해봐야할듯
        return smallCategoryList.stream()
                .map(
                        smallCategory -> {
                            List<FavorAnswer> favorAnswers =
                                    favorAnswerAdaptor.queryByUserIdAndSmallCategory(
                                            userId, smallCategory);
                            List<FavorAnswerContentVo> favorAnswerContentList = new ArrayList<>();
                            favorAnswers.forEach(
                                    favorAnswer ->
                                            favorAnswerContentList.add(
                                                    FavorAnswerContentVo.of(
                                                            favorAnswer
                                                                    .getFavorQuestion()
                                                                    .getFavorQuestionCategory()
                                                                    .getDetailCategory(),
                                                            favorAnswer
                                                                    .getFavorQuestion()
                                                                    .getNumber(),
                                                            favorAnswer.getAnswerContent())));

                            List<FavorAnswerCategoryDto> userAnswerCategories =
                                    favorAnswerCategoryDTOs.stream()
                                            .filter(
                                                    dto ->
                                                            dto.getSmallCategory()
                                                                    .equals(smallCategory))
                                            .toList();
                            // smallCategory와 같은 smallCategory를 가지는 favorAnswerCategoryDTO

                            List<DetailCategory> answeredDetailCategories =
                                    userAnswerCategories.stream()
                                            .map(FavorAnswerCategoryDto::getDetailCategory)
                                            .toList();

                            List<DetailCategory> notAnsweredDetailCategories =
                                    DetailCategory.getDetailCategoriesBySmallCategory(smallCategory)
                                            .stream()
                                            .filter(
                                                    detailCategory ->
                                                            !answeredDetailCategories.contains(
                                                                    detailCategory))
                                            .toList();

                            return UserAnswerVo.of(
                                    smallCategory,
                                    favorAnswerContentList,
                                    notAnsweredDetailCategories.isEmpty(),
                                    notAnsweredDetailCategories);
                        })
                .toList();
    }
}
