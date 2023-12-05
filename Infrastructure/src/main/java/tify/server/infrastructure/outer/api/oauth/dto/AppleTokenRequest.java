package tify.server.infrastructure.outer.api.oauth.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class AppleTokenRequest {

    private String code;
    private String clientId;
    private String clientSecret;
    private String grantType;
    private String refreshToken;

    @Builder
    public static AppleTokenRequest of(
            String code,
            String clientId,
            String clientSecret,
            String grantType,
            String refreshToken) {
        return AppleTokenRequest.builder()
                .code(code)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(grantType)
                .refreshToken(refreshToken)
                .build();
    }
}
