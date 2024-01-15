package tify.server.domain.domains.question.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.question.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long>, AnswerCustomRepository {

    Optional<Answer> findByQuestionIdAndUserId(Long questionId, Long userId);

    Long countAllByUserId(Long userId);

    void deleteAllByUserId(Long userId);
}
