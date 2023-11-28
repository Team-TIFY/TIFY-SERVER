package tify.server.domain.domains.question.adaptor;


import java.util.Optional;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.question.domain.AnswerReport;
import tify.server.domain.domains.question.exception.AnswerReportNotFoundException;
import tify.server.domain.domains.question.repository.AnswerReportRepository;

@Adaptor
@RequiredArgsConstructor
public class AnswerReportAdaptor {

    private final AnswerReportRepository answerReportRepository;

    public AnswerReport query(Long id) {
        return answerReportRepository
                .findById(id)
                .orElseThrow(() -> AnswerReportNotFoundException.EXCEPTION);
    }

    public Optional<AnswerReport> optionalQueryByUserIdAndAnswerId(Long userId, Long answerId) {
        return answerReportRepository.findByAnswerIdAndReportUserId(answerId, userId);
    }

    public void save(AnswerReport answerReport) {
        answerReportRepository.save(answerReport);
    }
}
