package tify.server.domain.domains.question.validator;


import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Validator;
import tify.server.domain.domains.question.adaptor.AnswerAdaptor;
import tify.server.domain.domains.question.adaptor.DailyQuestionAdaptor;
import tify.server.domain.domains.question.exception.AlreadyAnsweredQuestionException;
import tify.server.domain.domains.question.exception.NotValidTodayQuestionException;

@Validator
@RequiredArgsConstructor
public class QuestionValidator {

    private final DailyQuestionAdaptor dailyQuestionAdaptor;
    private final AnswerAdaptor answerAdaptor;

    public void isValidateAnswerToQuestion(Long questionId, Long userId) {
        // 질문이 오늘 날짜의 질문이 맞는지
        LocalDate now = LocalDate.now();
        if (!dailyQuestionAdaptor.query(questionId).getLoadingDate().isEqual(now)) {
            throw NotValidTodayQuestionException.EXCEPTION;
        }
        // 유저가 이미 해당 질문에 대한 답을 남겼는지
        if (answerAdaptor.optionalQueryByQuestionAndUser(questionId, userId).isPresent()) {
            throw AlreadyAnsweredQuestionException.EXCEPTION;
        }
    }
}
