package tify.server.domain.domains.question.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.question.domain.Knock;

public interface KnockRepository extends JpaRepository<Knock, Long>, KnockCustomRepository {

    List<Knock> findAllByDailyQuestionIdAndUserId(Long questionId, Long userId);

    List<Knock> findAllByDailyQuestionIdAndUserIdAndKnockedUserId(
            Long questionId, Long userId, Long knockedUserId);

    List<Knock> findDistinctByDailyQuestionIdAndKnockedUserId(Long questionId, Long knockedUserId);
}
