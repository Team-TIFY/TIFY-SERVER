package tify.server.infrastructure.outer.api.oauth.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import tify.server.infrastructure.outer.api.oauth.config.AppleAuthFeignConfig;
import tify.server.infrastructure.outer.api.oauth.dto.AppleRefreshRequest;
import tify.server.infrastructure.outer.api.oauth.dto.AppleRefreshResponse;
import tify.server.infrastructure.outer.api.oauth.dto.AppleTokenRequest;
import tify.server.infrastructure.outer.api.oauth.dto.AppleTokenResponse;

@FeignClient(
        name = "AppleOauthClient",
        url = "https://appleid.apple.com",
        configuration = AppleAuthFeignConfig.class)
public interface AppleOauthClient {

    @PostMapping(value = "/auth/token", consumes = "application/x-www-form-urlencoded")
    AppleTokenResponse appleAuth(AppleTokenRequest request);

    @PostMapping(value = "/auth/token", consumes = "application/x-www-form-urlencoded")
    AppleRefreshResponse appleAuth(AppleRefreshRequest request);
}
