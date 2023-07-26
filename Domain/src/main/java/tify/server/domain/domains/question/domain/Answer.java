package tify.server.domain.domains.question.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.core.consts.Status;
import tify.server.domain.domains.AbstractTimeStamp;

import static tify.server.core.consts.Status.*;

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
    
    @Enumerated(EnumType.STRING)
    private Status isDeleted;
    
    @Builder
    public Answer(Long userId, Long questionId, String content) {
        this.userId = userId;
        this.questionId = questionId;
        this.content = content;
        this.isDeleted = N;
    }
    
    public void delete() {
        this.isDeleted = Y;
    }
}
