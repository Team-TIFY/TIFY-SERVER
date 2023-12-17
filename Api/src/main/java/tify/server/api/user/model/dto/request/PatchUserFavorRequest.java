package tify.server.api.user.model.dto.request;


import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tify.server.domain.domains.user.domain.DetailCategory;

@Getter
@NoArgsConstructor
public class PatchUserFavorRequest {

    @NotNull private List<DetailCategory> userFavorDtoList;
}
