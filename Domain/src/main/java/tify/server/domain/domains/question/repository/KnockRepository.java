package tify.server.domain.domains.question.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.question.domain.Knock;

public interface KnockRepository extends JpaRepository<Knock, Long> {}
