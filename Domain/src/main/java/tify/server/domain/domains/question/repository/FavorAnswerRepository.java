package tify.server.domain.domains.question.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.domain.FavorQuestionCategory;

public interface FavorAnswerRepository
        extends JpaRepository<FavorAnswer, Long>, FavorAnswerCustomRepository {
    boolean existsByFavorQuestion_FavorQuestionCategoryAndUserId(
            FavorQuestionCategory favorQuestionCategory, Long userId);
}
