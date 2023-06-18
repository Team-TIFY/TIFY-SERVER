package tify.server.domain.domains.tag.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.AbstractTimeStamp;

@Getter
@Entity
@Table(name = "tbl_small_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmallCategory extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private String value;

    @NotNull private Long largeCategoryId;

    @Builder
    public SmallCategory(String value, Long largeCategoryId) {
        this.value = value;
        this.largeCategoryId = largeCategoryId;
    }
}
