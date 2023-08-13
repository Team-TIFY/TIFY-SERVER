package tify.server.domain.domains.question.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.question.domain.FavorQuestion;

public interface FavorQuestionRepository extends JpaRepository<FavorQuestion, Long> {}
