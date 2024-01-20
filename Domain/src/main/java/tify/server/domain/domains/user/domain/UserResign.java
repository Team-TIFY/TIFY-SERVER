package tify.server.domain.domains.user.domain;


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
@Table(name = "tbl_user_resign")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResign extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long userId;

    @NotNull private OauthInfo oauthInfo;

    @Builder
    public UserResign(Long userId, OauthInfo oauthInfo) {
        this.userId = userId;
        this.oauthInfo = oauthInfo;
    }
}
