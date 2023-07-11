package tify.server.domain.domains.user.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.common.vo.UserFavorVo;
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

    private Long largeCategoryId;

    @NotNull private Long smallCategoryId;

    private String thumbNailImageUrl;

    public UserFavorVo toUserFavorVo() {
        return UserFavorVo.from(new UserTag(0L, this.largeCategoryId));
    }

    @Builder
    public UserFavor(
            Long userTagId, Long largeCategoryId, Long smallCategoryId, String thumbNailImageUrl) {
        this.userTagId = userTagId;
        this.largeCategoryId = largeCategoryId;
        this.smallCategoryId = smallCategoryId;
        this.thumbNailImageUrl = thumbNailImageUrl;
    }
}
