package tify.server.domain.domains.question.domain;


import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.AbstractTimeStamp;

@Getter
@Entity
@Table(name = "tbl_answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long questionId;

    @NotNull private Long userId;

    @NotNull private String content;

    @NotNull
    @Column(unique = true)
    private LocalDate localDate;

    @Builder
    public Answer(Long userId, Long questionId, String content, LocalDate localDate) {
        this.userId = userId;
        this.questionId = questionId;
        this.content = content;
        this.localDate = localDate;
    }
}
