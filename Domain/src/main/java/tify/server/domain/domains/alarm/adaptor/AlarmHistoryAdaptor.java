package tify.server.domain.domains.alarm.adaptor;


import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
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
}
