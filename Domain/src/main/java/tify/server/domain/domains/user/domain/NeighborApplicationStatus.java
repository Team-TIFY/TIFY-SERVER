package tify.server.domain.domains.user.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NeighborApplicationStatus {
    REJECT("거절"),
    ACCEPT("수락"),
    WAIT("대기");

    final String value;
}
