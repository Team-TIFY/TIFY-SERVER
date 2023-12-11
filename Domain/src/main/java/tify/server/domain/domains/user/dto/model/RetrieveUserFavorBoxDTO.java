package tify.server.domain.domains.user.dto.model;


import java.util.List;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.user.domain.DetailCategory;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserFavor;

@Getter
@Builder
public class RetrieveUserFavorBoxDTO {

    private final String thumbnail;

    private final String userName;

    private final List<DetailCategory> userFavorList;

    private final String userOnBoardingStatus;

    public static RetrieveUserFavorBoxDTO from(User user) {
        return RetrieveUserFavorBoxDTO.builder()
                .thumbnail(user.getProfile().getThumbNail())
                .userName(user.getProfile().getUserName())
                .userFavorList(
                        user.getUserFavors().stream().map(UserFavor::getDetailCategory).toList())
                .userOnBoardingStatus(user.getOnBoardingStatus().getName())
                .build();
    }
}
