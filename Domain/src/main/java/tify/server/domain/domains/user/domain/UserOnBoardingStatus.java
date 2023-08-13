package tify.server.domain.domains.user.domain;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.AbstractTimeStamp;

@Getter
@Entity
@Table(name = "tbl_user_on_boarding_status")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserOnBoardingStatus extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String keyword;

    private Long length;

    @OneToMany(mappedBy = "onBoardingStatus")
    private final List<User> users = new ArrayList<>();

    @Builder
    public UserOnBoardingStatus(String name, String keyword, Long length) {
        this.name = name;
        this.keyword = keyword;
        this.length = length;
    }
}
