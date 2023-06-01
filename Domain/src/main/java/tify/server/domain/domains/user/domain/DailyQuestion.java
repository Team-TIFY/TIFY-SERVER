package tify.server.domain.domains.user.domain;

import com.esotericsoftware.kryo.serializers.FieldSerializer.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.AbstractTimeStamp;

import javax.persistence.*;
import java.sql.Timestamp;

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

}
