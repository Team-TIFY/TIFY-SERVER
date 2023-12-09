package tify.server.domain.domains.user.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.AbstractTimeStamp;

@Getter
@Entity
@Table(name = "tbl_user_favor")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFavor extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tbl_user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private DetailCategory detailCategory;

    @Column(name = "orderNumber")
    @NotNull
    private Long order;

    @Builder
    public UserFavor(User user, DetailCategory detailCategory, Long order) {
        this.user = user;
        this.detailCategory = detailCategory;
        this.order = order;
    }

    public void updateFavor(DetailCategory detailCategory, Long order) {
        this.detailCategory = detailCategory;
        this.order = order;
    }
}
