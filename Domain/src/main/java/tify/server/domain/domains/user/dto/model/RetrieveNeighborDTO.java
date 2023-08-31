package tify.server.domain.domains.user.dto.model;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RetrieveNeighborDTO {

    private Long neighborId;

    private String neighborThumbnail;

    private String neighborName;

    private String neighborBirth;

    private String onBoardingStatus;
}
