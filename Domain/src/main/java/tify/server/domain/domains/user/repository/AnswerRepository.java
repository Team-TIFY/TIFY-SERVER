package tify.server.domain.domains.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Long> {}
