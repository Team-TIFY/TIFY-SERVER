package tify.server.domain.domains.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.UserFavor;

public interface UserFavorRepository extends JpaRepository<UserFavor, Long> {}
