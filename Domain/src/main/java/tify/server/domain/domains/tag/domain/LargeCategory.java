package tify.server.domain.domains.tag.domain;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.AbstractTimeStamp;

@Getter
@Entity
@Table(name = "tbl_large_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LargeCategory extends AbstractTimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull private String value;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SmallCategory> smallCategories = new ArrayList<>();

    @Builder
    public LargeCategory(String value) {
        this.value = value;
    }

    public void updateSmallCategories(List<SmallCategory> smallCategories) {
        this.smallCategories.addAll(smallCategories);
    }
}
