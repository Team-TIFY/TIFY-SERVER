package tify.server.api.user.model.dto.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.user.domain.OpinionType;

@Getter
@NoArgsConstructor
public class PostUserOpinionRequest {

    @Schema(
            description = "문의의 유형입니다.",
            example = "기능 오류, 브랜드/상품 제안, 투데이/취향 질문 제안, 게시물/사용자 신고, 사용법 문의, 기타 문의",
            implementation = OpinionType.class)
    private OpinionType opinionType;

    @Schema(description = "문의 제목입니다.", example = "title")
    private String title;

    @Schema(description = "문의 내용입니다.", example = "content")
    private String content;

    @Schema(description = "답변 받을 이메일 주소입니다.", example = "aa@naver.com")
    private String email;

    @Schema(description = "첨부할 파일입니다.", example = "aa.jpg")
    private String file;
}
