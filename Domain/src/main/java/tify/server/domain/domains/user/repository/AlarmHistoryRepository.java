package tify.server.domain.domains.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.user.domain.AlarmHistory;

public interface AlarmHistoryRepository extends JpaRepository<AlarmHistory, Long> {}
