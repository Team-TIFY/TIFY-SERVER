package tify.server.domain.domains.question.validator;


import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Validator;
import tify.server.domain.domains.question.adaptor.FavorQuestionAdaptor;
import tify.server.domain.domains.question.domain.FavorQuestion;
import tify.server.domain.domains.question.exception.AlreadyAnsweredFavorQuestionException;

@Validator
@RequiredArgsConstructor
public class FavorQuestionValidator {

    private final FavorQuestionAdaptor favorQuestionAdaptor;

    public void isExistFavorQuestionCategory(String categoryName) {
        favorQuestionAdaptor.queryFavorQuestionCategoryByName(categoryName);
    }

    public void isValidateFavorAnswerToQuestion(List<FavorQuestion> favorQuestions, Long userId) {
        FavorQuestion favorQuestion = favorQuestions.get(0);
        // 유저가 이미 해당 질문에 대한 답을 남겼는지
        if (!favorQuestions.isEmpty()) {
            if (favorQuestionAdaptor.existQueryByFavorQuestionCategoryAndUser(
                    favorQuestion.getFavorQuestionCategory(), userId)) {
                throw AlreadyAnsweredFavorQuestionException.EXCEPTION;
            }
        }
    }
}
