package tify.server.infrastructure.outer.api.oauth.config;


import feign.Response;
import feign.codec.ErrorDecoder;
import tify.server.core.dto.ErrorDetail;
import tify.server.core.exception.OuterServerException;
import tify.server.infrastructure.outer.api.oauth.dto.AppleAuthErrorResponse;
import tify.server.infrastructure.outer.api.oauth.exception.AppleAuthErrorCode;

public class AppleAuthErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        AppleAuthErrorResponse body = AppleAuthErrorResponse.from(response);

        try {
            AppleAuthErrorCode appleAuthErrorCode = AppleAuthErrorCode.valueOf(body.getError());
            ErrorDetail errorDetail = appleAuthErrorCode.getErrorDetail();
            throw new OuterServerException(
                    errorDetail.getStatusCode(),
                    errorDetail.getErrorCode(),
                    errorDetail.getReason());
        } catch (IllegalArgumentException e) {
            AppleAuthErrorCode appleInvalidRequest = AppleAuthErrorCode.INVALID_REQUEST;
            ErrorDetail errorDetail = appleInvalidRequest.getErrorDetail();
            throw new OuterServerException(
                    errorDetail.getStatusCode(),
                    errorDetail.getErrorCode(),
                    errorDetail.getReason());
        }
    }
}
