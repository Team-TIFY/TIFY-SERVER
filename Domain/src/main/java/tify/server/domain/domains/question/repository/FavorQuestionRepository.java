package tify.server.domain.domains.question.repository;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.question.domain.FavorQuestion;

public interface FavorQuestionRepository extends JpaRepository<FavorQuestion, Long> {

    Optional<FavorQuestion> findByFavorQuestionCategory_NameAndNumber(String name, Long number);

    List<FavorQuestion> findAllByFavorQuestionCategory_NameOrderByNumber(String categoryName);
}
