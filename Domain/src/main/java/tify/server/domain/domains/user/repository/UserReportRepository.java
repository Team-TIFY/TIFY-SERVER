package tify.server.domain.domains.user.repository;


import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.UserReport;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {

    List<UserReport> findAllByToUserId(Long userId);

    Optional<UserReport> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId);

    void deleteAllByFromUserIdOrToUserId(Long fromUserId, Long toUserId);
}
