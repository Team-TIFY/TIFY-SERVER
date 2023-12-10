package tify.server.infrastructure.outer.api.oauth.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AppleOIDCPublicKeyDto {

    private String kty;
    private String kid;
    private String alg;
    private String use;
    private String n;
    private String e;
}
