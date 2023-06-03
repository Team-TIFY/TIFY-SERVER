package tify.server.domain.domains.alarm.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmType {
    ALARM("쿡 찌르기");

    final String type;
}
