package tify.server.domain.domains.question.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.question.domain.DailyQuestion;

public interface DailyQuestionRepository extends JpaRepository<DailyQuestion, Long> {}
