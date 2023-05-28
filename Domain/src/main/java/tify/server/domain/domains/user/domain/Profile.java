package tify.server.domain.domains.user.domain;


import javax.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    private String userName;

    private String email;

    private String thumbNail;

    @Builder
    public Profile(String userName, String email, String thumbNail) {
        this.userName = userName;
        this.email = email;
        this.thumbNail = thumbNail;
    }
}
