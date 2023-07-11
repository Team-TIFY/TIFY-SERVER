package tify.server.domain.domains.user.domain;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.AbstractTimeStamp;

@Getter
@Entity
@Table(name = "tbl_user_tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTag extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tbl_user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private LargeCategory largeCategory;

    @OneToMany(
            mappedBy = "userTagId",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<UserFavor> favors = new ArrayList<>();

    @Builder
    public UserTag(User user, LargeCategory largeCategory) {
        this.user = user;
        this.largeCategory = largeCategory;
    }

    public void updateUserFavors(List<UserFavor> favors) {
        this.favors.addAll(favors);
    }
}
