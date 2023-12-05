package tify.server.infrastructure.outer.api.oauth.dto;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import feign.Response;
import java.io.IOException;
import java.io.InputStream;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.infrastructure.exception.FeignException;

@Getter
@NoArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class AppleAuthErrorResponse {

    private String error;
    private String errorCode;
    private String errorDescription;

    public static AppleAuthErrorResponse from(Response res) {
        try (InputStream bodyIs = res.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(bodyIs, AppleAuthErrorResponse.class);
        } catch (IOException e) {
            throw FeignException.EXCEPTION;
        }
    }
}
