package tify.server.domain.domains.user.domain;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "tbl_neighbor")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Neighbor extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long fromUserId;

    @NotNull private Long toUserId;

    @NotNull
    @Column(name = "orderNumber")
    private Long order;

    @NotNull private Boolean isView;

    private Timestamp viewedAt;

    @NotNull private Boolean isNew;

    @Builder
    public Neighbor(Long fromUserId, Long toUserId, Long order, Boolean isView, Boolean isNew) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.order = order;
        this.isView = isView;
        this.viewedAt = Timestamp.valueOf(LocalDateTime.now());
        this.isNew = isNew;
    }

    public void updateOrder(Long changeOrder) {
        this.order = changeOrder;
    }

    public void updateIsView() {
        if (this.isView) {
            this.isView = false;
        } else {
            this.isView = true;
        }
    }

    public void updatedViewedAt() {
        this.viewedAt = Timestamp.valueOf(LocalDateTime.now());
    }

    public void updateIsNew() {
        this.isNew = !this.isNew;
    }
}
