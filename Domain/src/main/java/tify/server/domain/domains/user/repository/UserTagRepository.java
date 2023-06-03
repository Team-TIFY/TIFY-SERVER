package tify.server.domain.domains.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.UserTag;

public interface UserTagRepository extends JpaRepository<UserTag, Long> {}
