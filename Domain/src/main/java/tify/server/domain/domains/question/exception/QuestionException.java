package tify.server.domain.domains.question.exception;

import static tify.server.core.consts.StaticVal.BAD_REQUEST;
import static tify.server.core.consts.StaticVal.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.core.dto.ErrorDetail;
import tify.server.core.exception.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum QuestionException implements BaseErrorCode {
    DAILY_QUESTION_NOT_FOUND_ERROR(NOT_FOUND, "DailyQuestion_404_1", "일일 질문 정보를 찾을 수 없습니다."),
    ANSWER_NOT_FOUND_ERROR(NOT_FOUND, "Answer_404_1", "일일 질문에 대한 답변 정보를 찾을 수 없습니다."),
    KNOCK_NOT_FOUND_ERROR(NOT_FOUND, "Knock_404_1", "찌르기에 대한 답변 정보를 찾을 수 없습니다."),
    NOT_VALID_TODAY_QUESTION_ERROR(BAD_REQUEST, "Answer_400_1", "오늘의 질문이 아닙니다."),
    ALREADY_ANSWERED_QUESTION_ERROR(BAD_REQUEST, "Answer_400_2", "이미 답변하신 질문입니다."),
    MULTIPLE_ANSWER_ERROR(BAD_REQUEST, "Answer_400_3", "한 질문에 여러번 답할 수 없습니다."),
    FAVOR_QUESTION_NOT_FOUND_ERROR(NOT_FOUND, "FavorQuestion_404_1", "취향 질문 정보를 찾을 수 없습니다."),
    FAVOR_ANSWER_NOT_FOUND_ERROR(NOT_FOUND, "FavorAnswer_404_1", "취향 질문 답변 정보를 찾을 수 없습니다."),
    ALREADY_ANSWERED_FAVOR_QUESTION_ERROR(BAD_REQUEST, "FavorAnswer_400_2", "이미 답변하신 취향 질문입니다."),
    FAVOR_QUESTION_CATEGORY_NOT_FOUND_ERROR(
            NOT_FOUND, "FavorQuestionCategory_404_1", "취향 질문 카테고리 정보를 찾을 수 없습니다."),
    FAVOR_ANSWER_SIZE_NOT_MATCHED_CATEGORY_ERROR(BAD_REQUEST, "FavorAnswer_400_3", ""),
    NOT_VALID_ANSWER_REPORT_ERROR(BAD_REQUEST, "AnswerReport_400_1", "내 질문은 신고할 수 없습니다."),
    ANSWER_REPORT_NOT_FOUND_ERROR(NOT_FOUND, "AnswerReport_404_1", "답변에 대한 신고를 찾을 수 없습니다."),
    ;

    private final Integer statusCode;
    private final String errorCode;
    private final String reason;

    @Override
    public ErrorDetail getErrorDetail() {
        return ErrorDetail.of(statusCode, errorCode, reason);
    }
}
