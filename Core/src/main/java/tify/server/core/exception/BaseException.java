package tify.server.core.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.core.dto.ErrorDetail;

@Getter
@AllArgsConstructor
public class BaseException extends RuntimeException {

    private BaseErrorCode errorCode;

    public ErrorDetail getErrorDetail() {
        return this.errorCode.getErrorDetail();
    }
}
