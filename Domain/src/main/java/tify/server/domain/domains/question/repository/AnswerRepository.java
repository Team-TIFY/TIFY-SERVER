package tify.server.domain.domains.question.repository;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tify.server.domain.domains.question.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long>, AnswerCustomRepository {

    Optional<Answer> findByQuestionIdAndUserId(Long questionId, Long userId);

    @Query(
            nativeQuery = true,
            value =
                    "select * "
                            + "from tbl_answer a "
                            + "where a.question_id = :questionId "
                            + "order by createAt DESC ")
    List<Answer> findAllAnswer(@Param("questionId") Long questionId);
}
