package tify.server.domain.domains.question.exception;


import tify.server.core.exception.BaseException;

public class AlreadyAnsweredQuestionException extends BaseException {

    public static final BaseException EXCEPTION = new AlreadyAnsweredQuestionException();

    private AlreadyAnsweredQuestionException() {
        super(QuestionException.ALREADY_ANSWERED_QUESTION_ERROR);
    }
}
