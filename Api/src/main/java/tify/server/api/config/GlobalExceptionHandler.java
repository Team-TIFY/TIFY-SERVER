package tify.server.api.config;


import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.UriComponentsBuilder;
import tify.server.api.config.security.SecurityUtils;
import tify.server.api.config.slack.SlackApiProvider;
import tify.server.api.config.slack.SlackInternalErrorSender;
import tify.server.core.dto.ErrorDetail;
import tify.server.core.dto.ErrorResponse;
import tify.server.core.exception.BaseErrorCode;
import tify.server.core.exception.BaseException;
import tify.server.core.exception.GlobalException;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final SlackApiProvider slackApiProvider;
    private final SlackInternalErrorSender slackInternalErrorSender;

    //    protected ResponseEntity<Object> handleExceptionInternal(
    //            Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest
    // request) {
    //        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
    //        String url =
    //                UriComponentsBuilder.fromHttpRequest(
    //                                new ServletServerHttpRequest(servletWebRequest.getRequest()))
    //                        .build()
    //                        .toUriString();
    //
    //        ErrorResponse errorResponse =
    //                new ErrorResponse(ErrorDetail.of(status.value(), status.name(),
    // ex.getMessage()));
    //        return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
    //    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> internalServerExceptionHandle(
            Exception e, HttpServletRequest req) throws Exception {
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) req;
        final Long userId = SecurityUtils.getCurrentUserId();
        String url =
                UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(req))
                        .build()
                        .toUriString();

        log.info("INTERNAL_SERVER_ERROR {}", e.getMessage());
        GlobalException internalServerError = GlobalException.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(internalServerError.getErrorDetail());

        slackInternalErrorSender.execute(cachingRequest, e, userId);
        return ResponseEntity.status(HttpStatus.valueOf(internalServerError.getStatusCode()))
                .body(errorResponse);
    }

    @ExceptionHandler(BaseException.class)
    protected ResponseEntity<ErrorResponse> baseExceptionHandle(
            BaseException e, HttpServletRequest req) {
        BaseErrorCode code = e.getErrorCode();
        ErrorDetail errorDetail = code.getErrorDetail();
        ErrorResponse errorResponse = new ErrorResponse(errorDetail);
        return ResponseEntity.status(HttpStatus.valueOf(errorDetail.getStatusCode()))
                .body(errorResponse);
    }
}
