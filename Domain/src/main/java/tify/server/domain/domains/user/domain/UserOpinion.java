package tify.server.domain.domains.user.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.domain.domains.AbstractTimeStamp;

@Getter
@Entity
@Table(name = "tbl_user_opinion")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserOpinion extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private OpinionType opinionType;

    @ManyToOne
    @JoinColumn(name = "tbl_user_id")
    private User user;

    @NotNull private String title;

    @NotNull private String content;

    @NotNull private String email;

    private String file;

    @Builder
    public UserOpinion(
            OpinionType opinionType,
            User user,
            String title,
            String content,
            String email,
            String file) {
        this.opinionType = opinionType;
        this.user = user;
        this.title = title;
        this.content = content;
        this.email = email;
        this.file = file;
    }

    @Transactional
    public void updateTitle(String title) {
        this.title = title;
    }

    @Transactional
    public void updateContent(String content) {
        this.content = content;
    }

    @Transactional
    public void updateEmail(String email) {
        this.email = email;
    }
}
