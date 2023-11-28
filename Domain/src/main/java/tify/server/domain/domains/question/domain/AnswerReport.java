package tify.server.domain.domains.question.domain;


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
@Table(name = "tbl_answer_report")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerReport extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long answerId;

    @NotNull private Long reportUserId;

    @Builder
    public AnswerReport(Long answerId, Long reportUserId) {
        this.answerId = answerId;
        this.reportUserId = reportUserId;
    }
}
