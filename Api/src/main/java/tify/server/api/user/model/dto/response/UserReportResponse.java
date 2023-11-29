package tify.server.api.user.model.dto.response;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserReportResponse {

    private final Long fromUserId;
    private final Long toUserId;
    private final boolean reportSuccess;
}
