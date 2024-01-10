package tify.server.api.alarm.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tify.server.api.utils.AlarmHistoryUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.alarm.adaptor.AlarmHistoryAdaptor;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class RetrieveAlarmHistoryUseCase {

    private final AlarmHistoryAdaptor alarmHistoryAdaptor;
    private final AlarmHistoryUtils alarmHistoryUtils;
}
