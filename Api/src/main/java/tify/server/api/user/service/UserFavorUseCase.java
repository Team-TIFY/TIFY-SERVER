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
                            int userAnswerCategorySize =
                                    favorAnswerCategoryDTOs.stream()
                                            .filter(
                                                    dto ->
                                                            dto.getSmallCategory()
                                                                    .equals(smallCategory))
                                            .toList()
                                            .size();
                            // smallCategory와 같은 smallCategory를 가지는 favorAnswerCategoryDTO의 개수

                            int size =
                                    favorQuestionCategories.stream()
                                            .filter(
                                                    category ->
                                                            category.getSmallCategory()
                                                                    .equals(smallCategory))
                                            .toList()
                                            .size();
                            // smallCategory와 같은 smallCategory를 가지는 detailCategory의 개수

                            return UserAnswerVo.of(
                                    smallCategory,
                                    favorAnswerContentList,
                                    userAnswerCategorySize == size);
                        })
                .toList();
    }
}
