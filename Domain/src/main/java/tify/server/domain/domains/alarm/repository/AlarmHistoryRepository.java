package tify.server.domain.domains.alarm.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tify.server.core.consts.Status;
import tify.server.domain.domains.alarm.domain.AlarmHistory;

public interface AlarmHistoryRepository extends JpaRepository<AlarmHistory, Long> {

    List<AlarmHistory> findAllByUserId(Long userId);

    List<AlarmHistory> findAllByIsRead(Status isRead);

    List<AlarmHistory> findAllByTitle(String title);
}
