package tify.server.domain.domains.user.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.UserOnBoardingStatus;

public interface UserOnBoardingStatusRepository
        extends JpaRepository<UserOnBoardingStatus, Long>, UserOnBoardingCustomRepository {

    Optional<UserOnBoardingStatus> findByName(String name);
}
