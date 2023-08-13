package tify.server.domain.domains.question.domain;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.core.consts.Status;
import tify.server.domain.domains.AbstractTimeStamp;

@Getter
@Entity
@Table(name = "tbl_favor_question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FavorQuestion extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "favorQuestionCategoryId")
    private FavorQuestionCategory favorQuestionCategory;

    private Long number;

    private String questionContent;

    @Enumerated(EnumType.STRING)
    private Status isExternalExposure;
}
