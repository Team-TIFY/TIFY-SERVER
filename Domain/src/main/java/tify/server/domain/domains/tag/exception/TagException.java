package tify.server.domain.domains.tag.exception;

import static tify.server.core.consts.StaticVal.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.core.dto.ErrorDetail;
import tify.server.core.exception.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum TagException implements BaseErrorCode {
    LARGECATEGORY_NOT_FOUND_ERROR(NOT_FOUND, "LargeCategory_404_1", "태그 대분류 정보를 찾을 수 없습니다."),
    SMALLCATEGORY_NOT_FOUND_ERROR(NOT_FOUND, "SmallCategory_404_1", "태그 소분류 정보를 찾을 수 없습니다.");

    private final Integer statusCode;
    private final String errorCode;
    private final String reason;

    @Override
    public ErrorDetail getErrorDetail() {
        return null;
    }
}
