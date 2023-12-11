package tify.server.domain.domains.user.domain;


import javax.persistence.*;
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

    @Builder
    public UserFavor(User user, DetailCategory detailCategory) {
        this.user = user;
        this.detailCategory = detailCategory;
    }

    public void updateFavor(DetailCategory detailCategory) {
        this.detailCategory = detailCategory;
    }
}
