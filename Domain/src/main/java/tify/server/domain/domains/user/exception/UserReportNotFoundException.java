package tify.server.domain.domains.user.exception;


import tify.server.core.exception.BaseException;

public class UserReportNotFoundException extends BaseException {

    public static final BaseException EXCEPTION = new UserReportNotFoundException();

    private UserReportNotFoundException() {
        super(UserException.USER_REPORT_NOT_FOUND_ERROR);
    }
}
