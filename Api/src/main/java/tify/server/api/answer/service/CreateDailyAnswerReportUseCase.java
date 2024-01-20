package tify.server.api.answer.service;


import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.answer.model.response.AnswerReportResponse;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.AnswerReportAdaptor;
import tify.server.domain.domains.question.domain.AnswerReport;
import tify.server.domain.domains.question.validator.AnswerReportValidator;
import tify.server.domain.domains.question.validator.AnswerValidator;

@UseCase
@RequiredArgsConstructor
public class CreateDailyAnswerReportUseCase {

    private final AnswerReportAdaptor answerReportAdaptor;
    private final AnswerReportValidator answerReportValidator;
    private final AnswerValidator answerValidator;

    @Transactional
    public AnswerReportResponse execute(Long answerId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        answerValidator.isValidAnswer(answerId);
        Optional<AnswerReport> report =
                answerReportAdaptor.optionalQueryByUserIdAndAnswerId(currentUserId, answerId);
        if (report.isPresent()) {
            return AnswerReportResponse.builder()
                    .userId(currentUserId)
                    .answerId(answerId)
                    .reportSuccess(false)
                    .build();
        } else {
            answerReportValidator.isMyAnswer(currentUserId, answerId);
            answerReportAdaptor.save(
                    AnswerReport.builder().reportUserId(currentUserId).answerId(answerId).build());
            return AnswerReportResponse.builder()
                    .userId(currentUserId)
                    .answerId(answerId)
                    .reportSuccess(true)
                    .build();
        }
    }
}
