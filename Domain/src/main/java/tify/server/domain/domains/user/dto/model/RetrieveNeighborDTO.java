package tify.server.domain.domains.user.dto.model;


import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RetrieveNeighborDTO {

    private Long neighborId;

    private Long neighborUserId;

    private Long userId;

    private String neighborThumbnail;

    private String neighborName;

    private String neighborBirth;

    private String onBoardingStatus;

    private Long order;

    private boolean isView;

    private boolean isNew;

    private Timestamp updatedAt;

    private Timestamp viewedAt;
}
