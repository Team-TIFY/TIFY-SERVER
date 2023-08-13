package tify.server.domain.domains.question.exception;


import tify.server.core.exception.BaseException;

public class FavorQuestionCategoryNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new FavorQuestionCategoryNotFoundException();

    private FavorQuestionCategoryNotFoundException() {
        super(QuestionException.FAVOR_QUESTION_CATEGORY_NOT_FOUND_ERROR);
    }
}
