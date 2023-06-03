package tify.server.infrastructure.outer.api.oauth.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import tify.server.infrastructure.outer.api.oauth.config.KakaoInfoClientConfig;
import tify.server.infrastructure.outer.api.oauth.dto.KakaoInfoResponse;

@FeignClient(
        name = "KakaoInfoClient",
        url = "https://kapi.kakao.com",
        configuration = KakaoInfoClientConfig.class)
public interface KakaoInfoClient {

    @GetMapping("/v2/user/me")
    KakaoInfoResponse kakaoUserInfo(@RequestHeader("Authorization") String accessToken);

    // Todo: 회원 탈퇴 로직 때 구현
    //    @PostMapping(path = "/v1/user/unlink", consumes =
    // MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    //    void unlinkUser(
    //        @RequestHeader("Authorization") String adminKey, UnlinkKaKaoTarget unlinkKaKaoTarget);

}
