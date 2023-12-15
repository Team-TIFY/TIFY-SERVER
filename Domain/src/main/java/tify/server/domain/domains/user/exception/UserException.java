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
            BAD_REQUEST, "UserOnBoardingStatus_400_1", ", ' '를 제외한 검색어를 입력해주세요."),
    ALREADY_EXIST_NEIGHBOR_RELATIONSHIP_ERROR(BAD_REQUEST, "Neighbor_400_1", "이미 친구인 회원입니다."),
    SELF_NEIGHBOR_ERROR(BAD_REQUEST, "Neighbor_400_2", "스스로에게 친구 신청 할 수 없습니다."),
    NEIGHBOR_APPLICATION_NOT_FOUND_ERROR(
            NOT_FOUND, "Neighbor_Application_404_1", "친구 신청 내역을 찾을 수 없습니다."),
    NEIGHBOR_APPLICATION_NOT_MATCHED_TO_USER_ID_ERROR(
            BAD_REQUEST, "Neighbor_Application_400_1", "해당 유저에게 신청한 친구 신청이 아닙니다."),
    NEIGHBOR_APPLICATION_STATUS_NOT_WAIT_ERROR(
            BAD_REQUEST, "Neighbor_Application_400_2", "이미 수락 및 거절 된 신청입니다."),
    ALREADY_EXIST_USER_BLOCK_ERROR(BAD_REQUEST, "User_Block_400_1", "이미 차단 된 유저입니다."),
    USER_BLOCK_NOT_FOUND_ERROR(NOT_FOUND, "User_Block_404_1", "친구 차단 내역을 찾을 수 없습니다."),
    USER_REPORT_NOT_FOUND_ERROR(NOT_FOUND, "User_Report_404_1", "신고 내역을 찾을 수 없습니다."),
    USER_OPINION_NOT_FOUND_ERROR(NOT_FOUND, "User_Opinion_404_1", "문의사항을 찾을 수 없습니다."),
    USER_FAVOR_UPDATE_ERROR(BAD_REQUEST, "User_Favor_400_1", "유저 취향 상자의 개수가 3개를 초과합니다."),
    USER_ON_BOARDING_STATUS_NOT_FOUND_ERROR(
            NOT_FOUND, "User_OnBoardingStatus_404_1", "유저 온보딩 상태값을 찾을 수 없습니다."),
    ;

    private final Integer statusCode;
    private final String errorCode;
    private final String reason;

    @Override
    public ErrorDetail getErrorDetail() {
        return ErrorDetail.of(statusCode, errorCode, reason);
    }
}
