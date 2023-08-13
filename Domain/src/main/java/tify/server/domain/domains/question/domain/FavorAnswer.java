package tify.server.domain.domains.question.domain;


import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.AbstractTimeStamp;

@Getter
@Entity
@Table(name = "tbl_favor_answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavorAnswer extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //  @ManyToOne
    //  @JoinColumn(name = "favor_question_id")
    //  private FavorQuestion favorQuestion;

    private String answerContent;

    //  @Builder
    //  public FavorAnswer(FavorQuestion favorQuestion, String answerContent) {
    //    this.favorQuestion = favorQuestion;
    //    this.answerContent = answerContent;
    //  }
}
