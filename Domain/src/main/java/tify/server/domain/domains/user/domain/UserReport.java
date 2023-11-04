package tify.server.domain.domains.user.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import tify.server.domain.domains.AbstractTimeStamp;

@Getter
@Entity
@Table(name = "tbl_user_report")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserReport extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long fromUserId;

    @NotNull private Long toUserId;

    @NotNull private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder
    public UserReport(Long fromUserId, Long toUserId, String title, String content) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.title = title;
        this.content = content;
    }
}
