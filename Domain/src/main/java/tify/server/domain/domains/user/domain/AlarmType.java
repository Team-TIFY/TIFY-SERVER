package tify.server.domain.domains.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AlarmType {
    ALARM("쿡 찌르기");
//    ALARM("쿡 찌르기", false);

    final String type;
//    final boolean checked;
}
