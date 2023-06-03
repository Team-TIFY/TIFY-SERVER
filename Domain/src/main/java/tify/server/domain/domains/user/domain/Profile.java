package tify.server.domain.domains.user.domain;


import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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

    private String birth;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Builder
    public Profile(String userName, String email, String thumbNail) {
        this.userName = userName;
        this.email = email;
        this.thumbNail = thumbNail;
    }
}
