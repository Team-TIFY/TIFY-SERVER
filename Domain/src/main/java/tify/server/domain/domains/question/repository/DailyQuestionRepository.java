package tify.server.domain.domains.question.repository;


import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.question.domain.DailyQuestion;

public interface DailyQuestionRepository extends JpaRepository<DailyQuestion, Long> {

    Optional<DailyQuestion> findByLoadingDate(LocalDate loadingDate);
}
