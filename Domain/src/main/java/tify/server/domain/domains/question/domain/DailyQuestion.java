package tify.server.domain.domains.question.domain;


import com.esotericsoftware.kryo.serializers.FieldSerializer.NotNull;
import java.sql.Timestamp;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.AbstractTimeStamp;
import tify.server.domain.domains.user.domain.Category;

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
    private Category category;

    @NotNull private String content;

    @NotNull private Timestamp loadingDate;

    @Builder
    public DailyQuestion(Category category, String content, Timestamp loadingDate) {
        this.category = category;
        this.content = content;
        this.loadingDate = loadingDate;
    }
}
