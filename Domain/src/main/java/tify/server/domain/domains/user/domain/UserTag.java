package tify.server.domain.domains.user.domain;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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

    @NotNull private Long userId;

    @NotNull private Long largeCategoryId;

    @OneToMany(
            mappedBy = "userTagId",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<UserFavor> favors = new ArrayList<>();

    @Builder
    public UserTag(Long userId, Long largeCategoryId) {
        this.userId = userId;
        this.largeCategoryId = largeCategoryId;
    }

    public void updateUserFavors(List<UserFavor> favors) {
        this.favors.addAll(favors);
    }
}
