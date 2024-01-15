package tify.server.domain.domains.question.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.domain.FavorQuestionCategory;
import tify.server.domain.domains.user.domain.DetailCategory;

public interface FavorAnswerRepository
        extends JpaRepository<FavorAnswer, Long>, FavorAnswerCustomRepository {
    boolean existsByFavorQuestion_FavorQuestionCategoryAndUserId(
            FavorQuestionCategory favorQuestionCategory, Long userId);

    boolean existsByFavorQuestion_FavorQuestionCategory_DetailCategoryAndUserId(
            DetailCategory detailCategory, Long userId);

    void deleteAllByUserId(Long userId);
}
