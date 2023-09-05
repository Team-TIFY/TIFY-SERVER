package tify.server.domain.domains.question.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.domain.FavorQuestion;
import tify.server.domain.domains.question.domain.FavorQuestionCategory;

public interface FavorAnswerRepository extends JpaRepository<FavorAnswer, Long> {
    boolean existsByFavorQuestion_FavorQuestionCategoryAndUserId(
            FavorQuestionCategory favorQuestionCategory, Long userId);

    boolean existsFavorAnswerByFavorQuestionAndUserId(FavorQuestion favorQuestion, Long userId);
}
