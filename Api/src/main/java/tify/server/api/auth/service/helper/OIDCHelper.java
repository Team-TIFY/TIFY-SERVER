package tify.server.api.auth.service.helper;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tify.server.core.dto.OIDCDto;
import tify.server.core.jwt.JwtOIDCProvider;
import tify.server.infrastructure.outer.api.oauth.dto.OIDCPublicKeyDto;
import tify.server.infrastructure.outer.api.oauth.dto.OIDCResponse;

@RequiredArgsConstructor
@Component
public class OIDCHelper {

    private final JwtOIDCProvider jwtOIDCProvider;

    public String getKidFromUnsignedIdToken(String token, String iss, String aud) {
        return jwtOIDCProvider.getKidFromUnsignedTokenHeader(token, iss, aud);
    }

    public String getAlgfromUnsignedIdToken(String token, String iss, String aud) {
        return jwtOIDCProvider.getAlgFromUnsignedTokenHeader(token, iss, aud);
    }

    public OIDCDto getPayloadFromIdToken(
            String token, String iss, String aud, OIDCResponse oidcResponse) {
        String kid = getKidFromUnsignedIdToken(token, iss, aud);

        System.out.println("kid = " + kid);

        OIDCPublicKeyDto oidcPublicKeyDto =
                oidcResponse.getKeys().stream()
                        .filter(o -> o.getKid().equals(kid))
                        .findFirst()
                        .orElseThrow();

        return jwtOIDCProvider.getOIDCTokenBody(
                token, oidcPublicKeyDto.getN(), oidcPublicKeyDto.getE());
    }
}
