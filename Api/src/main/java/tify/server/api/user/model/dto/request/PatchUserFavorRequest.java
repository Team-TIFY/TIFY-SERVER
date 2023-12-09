package tify.server.api.user.model.dto.request;


import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.api.user.model.dto.UserFavorDto;

@Getter
@NoArgsConstructor
public class PatchUserFavorRequest {

    private List<UserFavorDto> userFavorDtoList;
}
