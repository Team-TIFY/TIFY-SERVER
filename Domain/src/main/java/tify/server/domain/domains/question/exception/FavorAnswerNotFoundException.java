package tify.server.domain.domains.question.exception;


import tify.server.core.exception.BaseException;

public class FavorAnswerNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new FavorAnswerNotFoundException();

    private FavorAnswerNotFoundException() {
        super(QuestionException.FAVOR_ANSWER_NOT_FOUND_ERROR);
    }
}
