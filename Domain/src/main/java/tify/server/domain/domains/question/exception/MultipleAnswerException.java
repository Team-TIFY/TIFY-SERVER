package tify.server.domain.domains.question.exception;


import tify.server.core.exception.BaseException;

public class MultipleAnswerException extends BaseException {

    public static final BaseException EXCEPTION = new MultipleAnswerException();

    private MultipleAnswerException() {
        super(QuestionException.MULTIPLE_ANSWER_ERROR);
    }
}
