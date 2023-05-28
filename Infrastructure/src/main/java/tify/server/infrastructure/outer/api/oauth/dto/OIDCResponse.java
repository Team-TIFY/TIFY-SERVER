package tify.server.infrastructure.outer.api.oauth.dto;


import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OIDCResponse {

    List<OIDCPublicKeyDto> keys;
}
