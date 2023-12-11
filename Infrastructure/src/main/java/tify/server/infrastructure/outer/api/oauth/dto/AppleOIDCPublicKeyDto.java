package tify.server.infrastructure.outer.api.oauth.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AppleOIDCPublicKeyDto {

    private String kty;
    private String kid;
    private String alg;
    private String use;
    private String n;
    private String e;
}
