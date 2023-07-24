package tify.server.api.auth.model.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCanRegisterResponse {

    @Schema(description = "유저가 회원가입 가능한지 여부", example = "true")
    private final boolean canRegister;

    public static UserCanRegisterResponse from(boolean canRegister) {
        return UserCanRegisterResponse.builder().canRegister(canRegister).build();
    }
}
