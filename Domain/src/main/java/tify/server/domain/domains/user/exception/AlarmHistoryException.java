package tify.server.domain.domains.user.exception;

import static tify.server.core.consts.StaticVal.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.core.dto.ErrorDetail;
import tify.server.core.exception.BaseErrorCode;


@Getter
@AllArgsConstructor
public enum AlarmHistoryException implements BaseErrorCode {
    ALARM_NOT_FOUND_ERROR(NOT_FOUND, "AlarmHistory_404_1", "알람 히스토리 정보를 찾을 수 없습니다.");

    private final Integer statusCode;
    private final String errorCode;
    private final String reason;

    @Override
    public ErrorDetail getErrorDetail() { return null; }
}
