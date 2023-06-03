package tify.server.domain.domains.question.domain;


import com.esotericsoftware.kryo.serializers.FieldSerializer.NotNull;
import javax.persistence.*;
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

    @NotNull private Long userId;

    @NotNull private String content;

    @Builder
    public Answer(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }
}
