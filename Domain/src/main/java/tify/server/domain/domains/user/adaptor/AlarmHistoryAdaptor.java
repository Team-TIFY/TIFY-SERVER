package tify.server.domain.domains.user.adaptor;

import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.user.domain.AlarmHistory;
import tify.server.domain.domains.user.exception.AlarmHistoryNotFoundException;
import tify.server.domain.domains.user.repository.AlarmHistoryRepository;

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
        return alarmHistoryRepository.save(alarmHistory);}
}
