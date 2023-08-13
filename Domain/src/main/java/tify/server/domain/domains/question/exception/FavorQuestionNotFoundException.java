package tify.server.domain.domains.question.exception;


import tify.server.core.exception.BaseException;

public class FavorQuestionNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new FavorQuestionNotFoundException();

    private FavorQuestionNotFoundException() {
        super(QuestionException.FAVOR_QUESTION_NOT_FOUND_ERROR);
    }
}
