package tify.server.domain.domains.question.exception;


import tify.server.core.exception.BaseException;

public class AlreadyAnsweredFavorQuestionException extends BaseException {
    public static final BaseException EXCEPTION = new AlreadyAnsweredFavorQuestionException();

    private AlreadyAnsweredFavorQuestionException() {
        super(QuestionException.ALREADY_ANSWERED_QUESTION_ERROR);
    }
}
