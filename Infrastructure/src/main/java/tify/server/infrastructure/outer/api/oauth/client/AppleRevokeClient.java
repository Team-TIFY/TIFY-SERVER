package tify.server.infrastructure.outer.api.oauth.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import tify.server.infrastructure.outer.api.oauth.config.AppleAuthFeignConfig;
import tify.server.infrastructure.outer.api.oauth.dto.AppleRevokeRequest;
import tify.server.infrastructure.outer.api.oauth.dto.AppleTokenResponse;

@FeignClient(
        name = "AppleRevokeClient",
        url = "https://appleid.apple.com",
        configuration = AppleAuthFeignConfig.class)
public interface AppleRevokeClient {

    @PostMapping(value = "/auth/revoke", consumes = "application/x-www-form-urlencoded")
    AppleTokenResponse appleRevoke(AppleRevokeRequest request);
}
