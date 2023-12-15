package tify.server.api.user.service;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.FavorAnswerAdaptor;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.user.domain.SmallCategory;
import tify.server.domain.domains.user.vo.FavorAnswerContentVo;
import tify.server.domain.domains.user.vo.UserAnswerVo;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFavorUseCase {

    private final FavorAnswerAdaptor favorAnswerAdaptor;

    public List<UserAnswerVo> execute(Long userId, List<SmallCategory> smallCategoryList) {
        return smallCategoryList.stream()
                .map(
                        smallCategory -> {
                            List<FavorAnswer> favorAnswers =
                                    favorAnswerAdaptor.searchByUserIdAndSmallCategory(
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
                            return UserAnswerVo.of(smallCategory, favorAnswerContentList);
                        })
                .toList();
    }
}
