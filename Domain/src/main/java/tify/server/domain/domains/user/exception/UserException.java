package tify.server.domain.domains.user.exception;

import static tify.server.core.consts.StaticVal.*;

import tify.server.core.dto.ErrorDetail;
import tify.server.core.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserException implements BaseErrorCode {
    ALREADY_EXIST_USER_ERROR(BAD_REQUEST, "User_400_1", "이미 회원인 유저입니다."),
    USER_NOT_FOUND_ERROR(NOT_FOUND, "User_404_1", "유저를 찾을 수 없습니다.");

    private final Integer statusCode;
    private final String errorCode;
    private final String reason;

    @Override
    public ErrorDetail getErrorDetail() {
        return null;
    }
}
