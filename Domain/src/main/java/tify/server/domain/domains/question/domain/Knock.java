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
@Table(name = "tbl_knock")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Knock extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long userId;

    @NotNull private Long knockedUserId;

    @NotNull private Long dailyAnswerId;

    @Builder
    public Knock(Long userId, Long knockedUserId, Long dailyAnswerId) {
        this.userId = userId;
        this.knockedUserId = knockedUserId;
        this.dailyAnswerId = dailyAnswerId;
    }
}
