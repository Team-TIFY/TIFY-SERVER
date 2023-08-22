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
@Table(name = "tbl_daily_question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyQuestion extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DailyQuestionCategory category;

    @NotNull private String content;

    @NotNull
    @Column(unique = true)
    private LocalDate loadingDate;

    @Builder
    public DailyQuestion(DailyQuestionCategory category, String content, LocalDate loadingDate) {
        this.category = category;
        this.content = content;
        this.loadingDate = loadingDate;
    }
}
