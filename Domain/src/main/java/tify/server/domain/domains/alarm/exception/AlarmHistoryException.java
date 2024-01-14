package tify.server.domain.domains.alarm.exception;

import static tify.server.core.consts.StaticVal.BAD_REQUEST;
import static tify.server.core.consts.StaticVal.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.core.dto.ErrorDetail;
import tify.server.core.exception.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum AlarmHistoryException implements BaseErrorCode {
    ALARM_NOT_FOUND_ERROR(NOT_FOUND, "AlarmHistory_404_1", "알람 히스토리 정보를 찾을 수 없습니다."),
    NOT_VALID_EXPO_TOKEN_ERROR(
            BAD_REQUEST, "AlarmHistory_400_1", "알람 히스토리 서비스를 이용하려면 EXPO TOKEN이 필요합니다."),
    EXPO_PUSH_TICKET_ERROR(BAD_REQUEST, "AlarmHistory_400_2", "알람 히스토리 expo 서비스 에러입니다."),
    ;

    private final Integer statusCode;
    private final String errorCode;
    private final String reason;

    @Override
    public ErrorDetail getErrorDetail() {
        return null;
    }
}
