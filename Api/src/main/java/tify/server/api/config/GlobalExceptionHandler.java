package tify.server.api.config;

import static tify.server.core.exception.GlobalException.METHOD_ARGUMENT_ERROR;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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
import tify.server.core.exception.OuterServerException;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final SlackApiProvider slackApiProvider;
    private final SlackInternalErrorSender slackInternalErrorSender;

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> internalServerExceptionHandle(
            Exception e, HttpServletRequest req) throws Exception {
        final ContentCachingRequestWrapper cachingRequest = (ContentCachingRequestWrapper) req;
        final Long userId = SecurityUtils.getCurrentUserId();
        String url =
                UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(req))
                        .build()
                        .toUriString();
        //        log.error(
        //                "서버 내부 오류 발생: {} {} errMessage={} \n detail={}\n",
        //                req.getMethod(),
        //                req.getRequestURI(),
        //                e.getMessage(),
        //                e.getCause());
        log.error(String.valueOf(e));
        e.printStackTrace();
        GlobalException internalServerError = GlobalException.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = new ErrorResponse(internalServerError.getErrorDetail());

        slackInternalErrorSender.execute(cachingRequest, e, userId);
        return ResponseEntity.status(HttpStatus.valueOf(internalServerError.getStatusCode()))
                .body(errorResponse);
    }

    @ExceptionHandler(OuterServerException.class)
    protected ResponseEntity<ErrorResponse> outerServerExceptionHandle(OuterServerException e) {
        ErrorDetail errorDetail =
                ErrorDetail.of(e.getStatusCode(), e.getErrorCode(), e.getReason());
        ErrorResponse errorResponse = new ErrorResponse(errorDetail);
        return ResponseEntity.status(HttpStatus.valueOf(errorDetail.getStatusCode()))
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> ArgumentNotValidHandle(
            MethodArgumentNotValidException exception, HttpServletRequest req) {
        ErrorDetail reason =
                ErrorDetail.builder()
                        .statusCode(METHOD_ARGUMENT_ERROR.getStatusCode())
                        .errorCode(METHOD_ARGUMENT_ERROR.getErrorCode())
                        .reason(
                                exception
                                        .getBindingResult()
                                        .getAllErrors()
                                        .get(0)
                                        .getDefaultMessage())
                        .build();
        ErrorResponse errorResponse = new ErrorResponse(reason);
        return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
    }
}
