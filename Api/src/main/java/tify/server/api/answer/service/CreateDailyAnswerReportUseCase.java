package tify.server.api.answer.service;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.answer.model.response.AnswerReportResponse;
import tify.server.api.config.security.SecurityUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.AnswerReportAdaptor;
import tify.server.domain.domains.question.domain.AnswerReport;
import tify.server.domain.domains.question.validator.AnswerReportValidator;

@UseCase
@RequiredArgsConstructor
public class CreateDailyAnswerReportUseCase {

    private final AnswerReportAdaptor answerReportAdaptor;
    private final AnswerReportValidator answerReportValidator;

    @Transactional
    public ResponseEntity<AnswerReportResponse> execute(Long answerId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Optional<AnswerReport> report =
                answerReportAdaptor.optionalQueryByUserIdAndAnswerId(currentUserId, answerId);
        if (report.isPresent()) {
            AnswerReportResponse response =
                    AnswerReportResponse.builder()
                            .userId(currentUserId)
                            .answerId(answerId)
                            .reportSuccess(false)
                            .build();
            return new ResponseEntity<>(response, new HttpHeaders(), BAD_REQUEST);
        } else {
            answerReportValidator.isMyAnswer(currentUserId, answerId);
            answerReportAdaptor.save(
                    AnswerReport.builder().reportUserId(currentUserId).answerId(answerId).build());
            AnswerReportResponse response =
                    AnswerReportResponse.builder()
                            .userId(currentUserId)
                            .answerId(answerId)
                            .reportSuccess(true)
                            .build();
            return new ResponseEntity<>(response, new HttpHeaders(), OK);
        }
    }
}
