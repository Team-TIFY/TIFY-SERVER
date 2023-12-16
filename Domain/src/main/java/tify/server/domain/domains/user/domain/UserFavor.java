package tify.server.domain.domains.user.domain;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Enumerated(EnumType.STRING)
    private SmallCategory smallCategory;

    //    public UserTagVo toUserFavorVo() {
    //        return UserTagVo.from(new UserTag(0L, this.largeCategoryId));
    //    }

    @Builder
    public UserFavor(
            Long userTagId,
            Long largeCategoryId,
            SmallCategory smallCategory,
            String thumbNailImageUrl) {
        this.userTagId = userTagId;
        this.smallCategory = smallCategory;
    }
}
