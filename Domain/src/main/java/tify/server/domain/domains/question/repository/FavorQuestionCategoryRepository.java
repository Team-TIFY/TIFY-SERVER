package tify.server.domain.domains.question.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.question.domain.FavorQuestionCategory;

public interface FavorQuestionCategoryRepository
        extends JpaRepository<FavorQuestionCategory, Long> {}
