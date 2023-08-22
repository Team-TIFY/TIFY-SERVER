package tify.server.domain.domains.user.exception;

import static tify.server.core.consts.StaticVal.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.core.dto.ErrorDetail;
import tify.server.core.exception.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum UserException implements BaseErrorCode {
    ALREADY_EXIST_USER_ERROR(BAD_REQUEST, "User_400_1", "이미 회원인 유저입니다."),
    NOT_NEIGHBOR_ERROR(FORBIDDEN, "Neighbor_403_1", "해당 유저와 친구가 아닙니다."),
    USER_NOT_FOUND_ERROR(NOT_FOUND, "User_404_1", "유저를 찾을 수 없습니다."),
    USER_FAVOR_NOT_FOUND_ERROR(NOT_FOUND, "UserFavor_404_1", "유저 취향 정보를 찾을 수 없습니다."),
    USER_TAG_NOT_FOUND_ERROR(NOT_FOUND, "UserTag_404_1", "유저 태그 정보를 찾을 수 없습니다."),
    NEIGHBOR_NOT_FOUND_ERROR(NOT_FOUND, "Neighbor_404_1", "친구 정보를 찾을 수 없습니다."),
    ALREADY_REGISTER_USER_ID_ERROR(BAD_REQUEST, "User_400_2", "이미 등록된 유저 아이디입니다."),
    ON_BOARDING_STATE_NOT_FOUND_ERROR(NOT_FOUND, "OnBoardingState_404_1", "온보딩 상태 값을 찾을 수 없습니다."),
    GENDER_NOT_FOUND_ERROR(NOT_FOUND, "Gender_404_1", "성별 정보를 찾을 수 없습니다."),
    ON_BOARDING_STATUS_FILTER_ERROR(
            BAD_REQUEST, "UserOnBoardingStatus_400_1", ", ' '를 제외한 검색어를 입력해주세요.");

    private final Integer statusCode;
    private final String errorCode;
    private final String reason;

    @Override
    public ErrorDetail getErrorDetail() {
        return ErrorDetail.of(statusCode, errorCode, reason);
    }
}
