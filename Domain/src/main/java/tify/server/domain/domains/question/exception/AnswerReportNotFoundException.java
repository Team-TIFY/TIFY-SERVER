package tify.server.domain.domains.question.exception;


import tify.server.core.exception.BaseException;

public class AnswerReportNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new AnswerReportNotFoundException();

    private AnswerReportNotFoundException() {
        super(QuestionException.ANSWER_REPORT_NOT_FOUND_ERROR);
    }
}
