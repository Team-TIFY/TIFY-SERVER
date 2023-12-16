package tify.server.infrastructure.outer.api.oauth.dto;


import com.fasterxml.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class AppleRefreshResponse {

    private String accessToken;
    private String tokenType;
    private Long expiresIn;
}
