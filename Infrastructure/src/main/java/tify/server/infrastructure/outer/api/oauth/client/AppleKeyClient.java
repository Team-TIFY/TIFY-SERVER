package tify.server.infrastructure.outer.api.oauth.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import tify.server.infrastructure.outer.api.oauth.config.AppleAuthFeignConfig;
import tify.server.infrastructure.outer.api.oauth.dto.ApplePublicKeyResponse;

@FeignClient(
        name = "AppleKeyClient",
        url = "https://appleid.apple.com",
        configuration = AppleAuthFeignConfig.class)
public interface AppleKeyClient {

    @GetMapping(value = "/auth/keys")
    ApplePublicKeyResponse getAppleAuthPublicKey();
}
