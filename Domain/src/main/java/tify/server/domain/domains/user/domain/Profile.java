package tify.server.domain.domains.user.domain;


import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Profile {

    private String userName;

    private String email;

    private String thumbNail;

    private String birth;

    private String job;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    public void updateProfile(
            String userName, String thumbNail, String birth, String job, Gender gender) {
        this.userName = userName;
        this.thumbNail = thumbNail;
        this.birth = birth;
        this.job = job;
        this.gender = gender;
    }

    public void onBoardingProfile(String username, String birth, Gender gender) {
        this.userName = username;
        this.birth = birth;
        this.gender = gender;
    }

    @Builder
    public Profile(
            String userName,
            String email,
            String thumbNail,
            String birth,
            String job,
            Gender gender) {
        this.userName = userName;
        this.email = email;
        this.thumbNail = thumbNail;
        this.birth = birth;
        this.job = job;
        this.gender = gender;
    }
}
