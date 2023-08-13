package tify.server.domain.domains.question.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.question.domain.FavorAnswer;

public interface FavorAnswerRepository extends JpaRepository<FavorAnswer, Long> {}
