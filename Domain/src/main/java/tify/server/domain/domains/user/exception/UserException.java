package tify.server.domain.domains.user.exception;

import static tify.server.core.consts.StaticVal.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.core.dto.ErrorDetail;
import tify.server.core.exception.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum UserException implements BaseErrorCode {
    ALREADY_EXIST_USER_ERROR(BAD_REQUEST, "User_400_1", "이미 회원인 유저입니다."),
    USER_NOT_FOUND_ERROR(NOT_FOUND, "User_404_1", "유저를 찾을 수 없습니다."),
    USER_FAVOR_NOT_FOUND_ERROR(NOT_FOUND, "UserFavor_404_1", "유저 취향 정보를 찾을 수 없습니다."),
    USER_TAG_NOT_FOUND_ERROR(NOT_FOUND, "UserTag_404_1", "유저 태그 정보를 찾을 수 없습니다."),
    NEIGHBOR_NOT_FOUND_ERROR(NOT_FOUND, "Neighbor_404_1", "친구 정보를 찾을 수 없습니다.");

    private final Integer statusCode;
    private final String errorCode;
    private final String reason;

    @Override
    public ErrorDetail getErrorDetail() {
        return ErrorDetail.of(statusCode, errorCode, reason);
    }
}
