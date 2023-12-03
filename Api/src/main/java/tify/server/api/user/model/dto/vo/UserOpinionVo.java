package tify.server.api.user.model.dto.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.Profile;
import tify.server.domain.domains.user.domain.UserOpinion;

@Getter
@Builder
public class UserOpinionVo {

    @Schema(description = "문의한 유저의 pk값입니다.")
    private final Long userId;

    @Schema(description = "문의한 유저의 프로필입니다.")
    private final Profile profile;

    @Schema(description = "문의 제목입니다.")
    private final String title;

    @Schema(description = "문의 내용입니다..")
    private final String content;

    @Schema(description = "답변받을 이메일입니다.")
    private final String email;

    @Schema(description = "첨부된 파일입니다.")
    private final String file;

    public static UserOpinionVo from(UserOpinion userOpinion) {
        return UserOpinionVo.builder()
                .userId(userOpinion.getUser().getId())
                .profile(userOpinion.getUser().getProfile())
                .title(userOpinion.getTitle())
                .content(userOpinion.getContent())
                .email(userOpinion.getEmail())
                .file(userOpinion.getFile())
                .build();
    }
}
