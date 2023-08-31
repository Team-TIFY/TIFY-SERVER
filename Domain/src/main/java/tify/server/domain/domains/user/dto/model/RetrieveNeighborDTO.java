package tify.server.domain.domains.user.dto.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RetrieveNeighborDTO {

    private Long neighborId;

    private String neighborUserId;

    private String neighborThumbnail;

    private String neighborName;

    private String neighborBirth;

    private String onBoardingStatus;

    private Long order;

    private boolean isView;
}
