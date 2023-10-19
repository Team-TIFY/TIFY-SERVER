package tify.server.domain.domains.user.domain;

import static tify.server.domain.domains.user.domain.NeighborApplicationStatus.ACCEPT;
import static tify.server.domain.domains.user.domain.NeighborApplicationStatus.REJECT;
import static tify.server.domain.domains.user.domain.NeighborApplicationStatus.WAIT;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.AbstractTimeStamp;

@Getter
@Entity
@Table(name = "tbl_neighbor_application")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NeighborApplication extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long fromUserId;

    @NotNull private Long toUserId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private NeighborApplicationStatus neighborApplicationStatus;

    @Builder
    public NeighborApplication(Long fromUserId, Long toUserId) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.neighborApplicationStatus = WAIT;
    }

    public void accept() {
        this.neighborApplicationStatus = ACCEPT;
    }

    public void reject() {
        this.neighborApplicationStatus = REJECT;
    }
}
