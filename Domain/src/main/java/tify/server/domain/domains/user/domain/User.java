package tify.server.domain.domains.user.domain;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.AbstractTimeStamp;

@Getter
@Entity
@Table(name = "tbl_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded private Profile profile;

    @Embedded private OauthInfo oauthInfo;

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    private String expoToken;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Neighbor> neighbors = new ArrayList<>();

    @Builder
    public User(Profile profile, OauthInfo oauthInfo, String expoToken) {
        this.profile = profile;
        this.oauthInfo = oauthInfo;
        this.accountRole = AccountRole.USER;
        this.expoToken = expoToken;
    }
}
