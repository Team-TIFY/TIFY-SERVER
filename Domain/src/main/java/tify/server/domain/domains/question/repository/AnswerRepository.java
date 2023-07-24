package tify.server.domain.domains.question.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.question.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    Optional<Answer> findByQuestionIdAndUserId(Long questionId, Long userId);
}
