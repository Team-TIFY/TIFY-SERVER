package tify.server.domain.domains.alarm.adaptor;


import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.core.consts.Status;
import tify.server.domain.domains.alarm.domain.AlarmHistory;
import tify.server.domain.domains.alarm.exception.AlarmHistoryNotFoundException;
import tify.server.domain.domains.alarm.repository.AlarmHistoryRepository;

@Adaptor
@RequiredArgsConstructor
public class AlarmHistoryAdaptor {
    private final AlarmHistoryRepository alarmHistoryRepository;

    public AlarmHistory query(Long alarmHistoryId) {
        return alarmHistoryRepository
                .findById(alarmHistoryId)
                .orElseThrow(() -> AlarmHistoryNotFoundException.EXCEPTION);
    }

    public AlarmHistory save(AlarmHistory alarmHistory) {
        return alarmHistoryRepository.save(alarmHistory);
    }

    public List<AlarmHistory> queryByUserId(Long userId) {
        return alarmHistoryRepository.findAllByUserId(userId);
    }

    public List<AlarmHistory> queryByIsRead(Status isRead) {
        return alarmHistoryRepository.findAllByIsRead(isRead);
    }

    public List<AlarmHistory> queryByTitle(String title) {
        return alarmHistoryRepository.findAllByTitle(title);
    }
}
