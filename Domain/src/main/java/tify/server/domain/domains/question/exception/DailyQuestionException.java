package tify.server.domain.domains.question.exception;

import static tify.server.core.consts.StaticVal.BAD_REQUEST;
import static tify.server.core.consts.StaticVal.NOT_FOUND;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.core.dto.ErrorDetail;
import tify.server.core.exception.BaseErrorCode;

@Getter
@AllArgsConstructor
public enum DailyQuestionException implements BaseErrorCode {
    DAILY_QUESTION_NOT_FOUND_ERROR(NOT_FOUND, "DailyQuestion_404_1", "일일 질문 정보를 찾을 수 없습니다."),
    ANSWER_NOT_FOUND_ERROR(NOT_FOUND, "Answer_404_1", "일일 질문에 대한 답변 정보를 찾을 수 없습니다."),
    KNOCK_NOT_FOUND_ERROR(NOT_FOUND, "Knock_404_1", "찌르기에 대한 답변 정보를 찾을 수 없습니다."),
    NOT_VALID_TODAY_QUESTION_ERROR(BAD_REQUEST, "Answer-400-1", "오늘의 질문이 아닙니다."),
    ALREADY_ANSWERED_QUESTION_ERROR(BAD_REQUEST, "Answer-400-2", "이미 답변하신 질문입니다.");

    private final Integer statusCode;
    private final String errorCode;
    private final String reason;

    @Override
    public ErrorDetail getErrorDetail() {
        return null;
    }
}
