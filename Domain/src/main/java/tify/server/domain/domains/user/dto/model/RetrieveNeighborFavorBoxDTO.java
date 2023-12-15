package tify.server.domain.domains.user.dto.model;


import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import tify.server.domain.domains.user.domain.User;

@Getter
@AllArgsConstructor
public class RetrieveNeighborFavorBoxDTO {

    private final User user;

    private final Timestamp viewedAt;
}
