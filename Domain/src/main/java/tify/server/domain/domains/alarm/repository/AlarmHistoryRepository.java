package tify.server.domain.domains.alarm.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.domain.domains.alarm.domain.AlarmHistory;

public interface AlarmHistoryRepository extends JpaRepository<AlarmHistory, Long> {}
