package tify.server.domain.domains.user.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OpinionType {
    FUNCTION_ERROR("기능 오류"),
    BRAND_PRODUCT("브랜드/상품 제안"),
    DAILY_FAVOR_QUESTION("투데이/취향 질문 제안"),
    REPORT("게시물/사용자 신고"),
    HOW_TO_USE("사용법 문의"),
    OTHER("기타 문의");

    final String value;
}
