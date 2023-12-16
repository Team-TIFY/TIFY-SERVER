package tify.server.infrastructure.outer.api.oauth.exception;

import static tify.server.core.consts.StaticVal.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.core.dto.ErrorDetail;
import tify.server.core.exception.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum AppleAuthErrorCode implements BaseErrorCode {
    INVALID_REQUEST(
            BAD_REQUEST,
            "APPLE_400_1",
            "invalid_request",
            "매개변수가 누락되었거나, 지원되지 않는 매개변수가 포함되어 있거나, 여러 자격 증명이 포함되어 있거나, 클라이언트 인증을 위해 둘 이상의 메커니즘을 사용한 경우"),
    INVALID_CLIENT(
            BAD_REQUEST,
            "APPLE_400_2",
            "invalid_client",
            "잘못된 클라이언트 ID, 암호를 사용했거나 잘못된 Redirect URL을 사용한 경우"),
    INVALID_GRANT(
            BAD_REQUEST,
            "APPLE_400_3",
            "invalid_grant",
            "잘못된 클라이언트 ID를 사용했거나 만료 혹은 이전에 사용한 인증코드를 사용한 경우 혹은 잘못된 Refresh Token을 사용한 경우"),
    UNAUTHORIZED_CLIENT(UNAUTHORIZED, "APPLE_401_1", "unauthorized_client", "권한이 없는 클라이언트인 경우"),
    UNSUPPORTED_GRANT_TYPE(
            UNAUTHORIZED, "APPLE_401_1", "unauthorized_client", "인증된 클라이언트인데 잘못된 부여 유형을 사용한 경우"),
    INVALID_SCOPE(BAD_REQUEST, "APPLE_400_4", "invalid_scope", "스코프 형식이 잘못된 경우"),
    ;

    private Integer status;
    private String errorCode;
    private String error;
    private String reason;

    @Override
    public ErrorDetail getErrorDetail() {
        return ErrorDetail.of(status, errorCode, reason);
    }
}
