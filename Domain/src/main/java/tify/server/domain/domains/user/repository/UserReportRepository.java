package tify.server.domain.domains.user.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.UserReport;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {}
