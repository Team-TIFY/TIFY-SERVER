package tify.server.domain.domains.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.DailyQuestion;

public interface DailyQuestionRepository extends JpaRepository<DailyQuestion, Long> {}
