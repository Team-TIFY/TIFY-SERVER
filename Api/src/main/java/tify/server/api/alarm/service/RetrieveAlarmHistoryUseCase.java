package tify.server.api.alarm.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.alarm.model.condition.AlarmCondition;
import tify.server.api.alarm.model.vo.AlarmHistoryVo;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.alarm.adaptor.AlarmHistoryAdaptor;

@Slf4j
@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrieveAlarmHistoryUseCase {

    private final AlarmHistoryAdaptor alarmHistoryAdaptor;

    public List<AlarmHistoryVo> execute(AlarmCondition condition) {
        List<AlarmHistoryVo> alarms = new ArrayList<>();
        Optional.ofNullable(condition.getUserId())
                .ifPresent(
                        id ->
                                alarms.addAll(
                                        alarmHistoryAdaptor.queryByUserId(id).stream()
                                                .map(AlarmHistoryVo::from)
                                                .toList()));
        Optional.ofNullable(condition.getTitle())
                .ifPresent(
                        title ->
                                alarms.addAll(
                                        alarmHistoryAdaptor.queryByTitle(title).stream()
                                                .map(AlarmHistoryVo::from)
                                                .toList()));
        Optional.ofNullable(condition.getStatus())
                .ifPresent(
                        status ->
                                alarms.addAll(
                                        alarmHistoryAdaptor.queryByIsRead(status).stream()
                                                .map(AlarmHistoryVo::from)
                                                .toList()));
        return alarms;
    }
}
