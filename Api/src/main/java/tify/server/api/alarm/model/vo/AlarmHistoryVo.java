package tify.server.api.alarm.model.vo;


import lombok.Builder;
import lombok.Getter;
import tify.server.core.consts.Status;
import tify.server.domain.domains.alarm.domain.AlarmHistory;
import tify.server.domain.domains.alarm.domain.AlarmType;

@Getter
@Builder
public class AlarmHistoryVo {

    private Long id;

    private AlarmType alarmType;

    private String title;

    private String content;

    private Long userId;

    private Status isRead;

    public static AlarmHistoryVo from(AlarmHistory alarmHistory) {
        return AlarmHistoryVo.builder()
                .id(alarmHistory.getId())
                .alarmType(alarmHistory.getAlarmType())
                .title(alarmHistory.getTitle())
                .content(alarmHistory.getContent())
                .userId(alarmHistory.getUserId())
                .isRead(alarmHistory.getIsRead())
                .build();
    }
}
