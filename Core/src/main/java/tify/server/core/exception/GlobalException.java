package tify.server.core.exception;

import static tify.server.core.consts.StaticVal.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.core.dto.ErrorDetail;

@Getter
@AllArgsConstructor
public enum GlobalException implements BaseErrorCode {
    EXAMPLE_ERROR(BAD_REQUEST, "400-0", "에러 예시 입니다."),
    INTERNAL_SERVER_ERROR(SERVER_ERROR, "500-1", "서버 내부 오류입니다."),
    EXPIRED_TOKEN_ERROR(UNAUTHORIZED, "401-1", "토큰이 만료되었습니다."),
    INVALID_TOKEN_ERROR(UNAUTHORIZED, "401-2", "올바르지 않은 토큰입니다."),
    EXPIRED_REFRESH_TOKEN_ERROR(UNAUTHORIZED, "401-3", "리프레시 토큰이 만료되었습니다."),
    NOT_VALID_ACCESS_TOKEN_ERROR(UNAUTHORIZED, "401-4", "알맞은 accessToken을 넣어주세요."),
    SECURITY_CONTEXT_NOT_FOUND_ERROR(SERVER_ERROR, "500-2", "Security Context 에러입니다."),
    FEIGN_SERVER_ERROR(BAD_REQUEST, "400-0", "Feign 요청 시 외부 서버 오류입니다."),
    OUTER_SERVER_UNAUTHORIZED_EXCEPTION(UNAUTHORIZED, "Feign-401-1", "외부 서버 401 오류입니다."),
    OUTER_SERVER_FORBIDDEN_EXCEPTION(FORBIDDEN, "Feign-403-1", "외부 서버 403 오류입니다."),
    OUTER_SERVER_BAD_REQUEST_EXCEPTION(BAD_REQUEST, "Feign-400-1", "외부 서버 400 오류입니다."),
    OUTER_SERVER_EXPIRED_TOKEN_EXCEPTION(BAD_REQUEST, "Feign-400-2", "외부 서버 토큰 만료 오류입니다.");

    private final Integer statusCode;
    private final String errorCode;
    private final String reason;

    @Override
    public ErrorDetail getErrorDetail() {
        return ErrorDetail.of(statusCode, errorCode, reason);
    }
}
