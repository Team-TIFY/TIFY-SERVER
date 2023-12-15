package tify.server.api.user.model.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatchUserProfileRequest {

    @Schema(description = "수정할 사용자의 이름", example = "홍길동")
    private String username;

    @Schema(description = "수정할 사용자의 생일", example = "19990606")
    private String birth;

    @Schema(description = "수정할 사용자의 직업", example = "간호사")
    private String job;

    @Schema(description = "수정할 사용자의 성별", example = "MALE")
    private String gender;

    @Schema(description = "수정할 사용자의 프로필 이미지", example = "test.com")
    private String thumbnail;

    @Schema(description = "수정할 사용자의 ID", example = "asdfzxcv")
    private String userId;

    @Schema(
            description = "수정할 사용자의 온보딩 상태",
            example = "산악자전거 구입 중 \uD83D\uDEB5\u200D\uFE0F\uD83C\uDFDE\uFE0F")
    private String onBoardingStatus;
}
