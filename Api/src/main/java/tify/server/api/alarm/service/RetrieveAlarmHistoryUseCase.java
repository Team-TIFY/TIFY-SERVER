package tify.server.api.alarm.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.alarm.model.vo.AlarmHistoryVo;
import tify.server.core.annotation.UseCase;
import tify.server.core.consts.Status;
import tify.server.domain.domains.alarm.adaptor.AlarmHistoryAdaptor;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrieveAlarmHistoryUseCase {

    private final AlarmHistoryAdaptor alarmHistoryAdaptor;

    public List<AlarmHistoryVo> executeToUser(Long userId) {
        return alarmHistoryAdaptor.queryByUserId(userId).stream()
                .map(AlarmHistoryVo::from)
                .toList();
    }

    public List<AlarmHistoryVo> executeToIsRead(Status isRead) {
        return alarmHistoryAdaptor.queryByIsRead(isRead).stream()
                .map(AlarmHistoryVo::from)
                .toList();
    }

    public List<AlarmHistoryVo> executeToTitle(String title) {
        return alarmHistoryAdaptor.queryByTitle(title).stream().map(AlarmHistoryVo::from).toList();
    }
}
