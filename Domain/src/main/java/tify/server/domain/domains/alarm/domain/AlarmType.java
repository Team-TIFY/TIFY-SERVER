package tify.server.domain.domains.alarm.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmType {
    FRIEND("프렌즈"),
    TODAY("투데이 질문"),
    ANNIVERSARY("기념일"),
    FAVOR("취향 질문"),
    ;

    final String type;
}
