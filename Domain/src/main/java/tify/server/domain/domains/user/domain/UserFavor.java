package tify.server.domain.domains.user.domain;


import com.esotericsoftware.kryo.serializers.FieldSerializer.NotNull;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.AbstractTimeStamp;

@Getter
@Entity
@Table(name = "tbl_user_favor")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFavor extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private Long userTagId;

    @NotNull private String content;

    private String thumbNailImageUrl;
}
