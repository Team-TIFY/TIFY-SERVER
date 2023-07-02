package tify.server.api.config;


import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
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

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        String url =
                UriComponentsBuilder.fromHttpRequest(
                                new ServletServerHttpRequest(servletWebRequest.getRequest()))
                        .build()
                        .toUriString();

        ErrorResponse errorResponse =
                new ErrorResponse(ErrorDetail.of(status.value(), status.name(), ex.getMessage()));
        return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> internalServerExceptionHandle(
            Exception e, HttpServletRequest req) throws IOException {
        //        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper)
        // req;
        String url =
                UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(req))
                        .build()
                        .toUriString();

        log.error("INTERNAL_SERVER_ERROR", e);
        GlobalException internalServerError = GlobalException.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(internalServerError.getErrorDetail());

        //        slackInternalErrorSender.execute(cachingRequest, e, userId);
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

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request)
            throws Exception {
        final ContentCachingRequestWrapper cachingRequestWrapper =
                (ContentCachingRequestWrapper) request;
        final Long userId = SecurityUtils.getCurrentUserId();
        String url =
                UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request))
                        .build()
                        .toUriString();
        log.error("INTERNAL_SERVER_ERROR", e);
        GlobalException internalServerError = GlobalException.INTERNAL_SERVER_ERROR;

        ErrorDetail errorDetail =
                ErrorDetail.builder()
                        .statusCode(internalServerError.getStatusCode())
                        .errorCode(internalServerError.getErrorCode())
                        .reason(internalServerError.getReason())
                        .build();

        ErrorResponse errorResponse = new ErrorResponse(errorDetail);

        //        slackApiProvider.sendError(cachingRequestWrapper, e, userId); // provider로
        // mocking?

        slackInternalErrorSender.execute(cachingRequestWrapper, e, userId);
        return ResponseEntity.status(HttpStatus.valueOf(errorDetail.getStatusCode()))
                .body(errorResponse);
    }
}
