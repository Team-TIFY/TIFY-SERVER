package tify.server.domain.domains.alarm.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.core.consts.Status;

@Getter
@Entity
@Table(name = "tbl_alarmhistory")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlarmHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @NotNull private String content;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status isRead;

    @Builder
    public AlarmHistory(Long id, Long userId, AlarmType alarmType, String content) {
        this.id = id;
        this.userId = userId;
        this.alarmType = alarmType;
        this.content = content;
        this.isRead = Status.N;
    }
}
