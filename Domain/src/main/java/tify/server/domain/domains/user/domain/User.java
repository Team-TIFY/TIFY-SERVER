package tify.server.domain.domains.user.domain;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tify.server.domain.domains.AbstractTimeStamp;
import tify.server.domain.domains.user.vo.UserInfoVo;
import tify.server.domain.domains.user.vo.UserProfileVo;

@Slf4j
@Getter
@Entity
@Table(name = "tbl_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tbl_user_id")
    private Long id;

    @Column(unique = true)
    private String userId;

    @Embedded private Profile profile;

    @Embedded private OauthInfo oauthInfo;

    @Enumerated(EnumType.STRING)
    private AccountRole accountRole;

    private String expoToken;

    private String appleRefreshToken;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<UserTag> userTags = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onBoardingStatusId")
    private UserOnBoardingStatus onBoardingStatus;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<UserOpinion> userOpinions = new ArrayList<>();

    public UserInfoVo toUserInfoVo() {
        return UserInfoVo.from(this);
    }

    public UserProfileVo toUserProfileVo() {
        return UserProfileVo.from(this);
    }

    @Builder
    public User(Profile profile, OauthInfo oauthInfo, String expoToken) {
        this.profile = profile;
        this.oauthInfo = oauthInfo;
        this.accountRole = AccountRole.USER;
        this.expoToken = expoToken;
    }

    public void updateProfile(Profile profile) {
        this.profile = profile;
    }

    @Builder
    public User(OauthInfo oauthInfo) {
        this.oauthInfo = oauthInfo;
    }

    public void onBoarding(
            String username,
            String userId,
            String birth,
            Gender gender,
            UserOnBoardingStatus onBoardingStatus) {
        //        this.profile.onBoardingProfile(username, birth, gender);
        this.profile = Profile.builder().userName(username).birth(birth).gender(gender).build();
        this.userId = userId;
        this.onBoardingStatus = onBoardingStatus;
    }

    public void updateFavor() {
        setUpdatedAt();
    }

    public void updateAppleRefreshToken(String appleRefreshToken) {
        this.appleRefreshToken = appleRefreshToken;
    }

    public void updateUserId(String userId) {
        this.userId = userId;
    }

    public void updateOnBoardingStatus(UserOnBoardingStatus userOnBoardingStatus) {
        this.onBoardingStatus = userOnBoardingStatus;
    }
}
