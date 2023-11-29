package tify.server.domain.domains.question.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.question.domain.AnswerReport;

public interface AnswerReportRepository extends JpaRepository<AnswerReport, Long> {

    Optional<AnswerReport> findByAnswerIdAndReportUserId(Long answerId, Long reportUserId);
}
