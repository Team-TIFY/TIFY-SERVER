package tify.server.domain.domains.question.exception;


import tify.server.core.exception.BaseException;

public class NotValidAnswerReportException extends BaseException {

    public static final BaseException EXCEPTION = new NotValidAnswerReportException();

    private NotValidAnswerReportException() {
        super(QuestionException.NOT_VALID_ANSWER_REPORT_ERROR);
    }
}
