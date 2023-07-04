package tify.server.infrastructure.outer.api.oauth.config;


import feign.Response;
import feign.codec.ErrorDecoder;
import tify.server.core.dto.ErrorDetail;
import tify.server.core.exception.OuterServerException;
import tify.server.infrastructure.outer.api.oauth.dto.KakaoAuthErrorResponse;
import tify.server.infrastructure.outer.api.oauth.exception.KakaoAuthErrorCode;

public class KakaoAuthErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        KakaoAuthErrorResponse body = KakaoAuthErrorResponse.from(response);

        try {
            KakaoAuthErrorCode kakaoKauthErrorCode =
                    KakaoAuthErrorCode.valueOf(body.getErrorCode());
            ErrorDetail errorDetail = kakaoKauthErrorCode.getErrorDetail();
            System.out.println("errorDetail.getStatusCode() = " + errorDetail.getErrorCode());
            throw new OuterServerException(
                    errorDetail.getStatusCode(),
                    errorDetail.getErrorCode(),
                    errorDetail.getReason());
        } catch (IllegalArgumentException e) {
            KakaoAuthErrorCode koeInvalidRequest = KakaoAuthErrorCode.KOE_INVALID_REQUEST;
            ErrorDetail errorDetail = koeInvalidRequest.getErrorDetail();
            throw new OuterServerException(
                    errorDetail.getStatusCode(),
                    errorDetail.getErrorCode(),
                    errorDetail.getReason());
        }
    }
}
