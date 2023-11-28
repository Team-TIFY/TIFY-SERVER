package tify.server.domain.domains.version.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.core.consts.StaticVal;
import tify.server.core.dto.ErrorDetail;
import tify.server.core.exception.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum VersionException implements BaseErrorCode {
    VERSION_NOT_FOUND_ERROR(StaticVal.NOT_FOUND, "Version_404_1", "버전 정보를 찾을 수 없습니다."),
    ;

    private final Integer statusCode;
    private final String errorCode;
    private final String reason;

    @Override
    public ErrorDetail getErrorDetail() {
        return ErrorDetail.of(statusCode, errorCode, reason);
    }
}
