package tify.server.domain.domains.question.domain;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import tify.server.domain.domains.user.domain.DetailCategory;
import tify.server.domain.domains.user.domain.LargeCategory;
import tify.server.domain.domains.user.domain.SmallCategory;

@Getter
@Entity
@Table(name = "tbl_favor_question_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavorQuestionCategory extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private LargeCategory largeCategory;

    @Enumerated(EnumType.STRING)
    private SmallCategory smallCategory;

    @Enumerated(EnumType.STRING)
    private DetailCategory detailCategory;

    @OneToMany(mappedBy = "favorQuestionCategory")
    private final List<FavorQuestion> favorQuestions = new ArrayList<>();

    @Builder
    public FavorQuestionCategory(
            String name,
            LargeCategory largeCategory,
            SmallCategory smallCategory,
            DetailCategory detailCategory) {
        this.name = name;
        this.largeCategory = largeCategory;
        this.smallCategory = smallCategory;
        this.detailCategory = detailCategory;
    }
}
