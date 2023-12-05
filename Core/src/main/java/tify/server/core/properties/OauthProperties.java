package tify.server.core.properties;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "oauth2")
public class OauthProperties {

    private KakaoSecret kakao;
    private AppleSecret apple;

    @Getter
    @Setter
    public static class KakaoSecret {
        private String baseUrl;
        private String clientId;
        private String clientSecret;
        private String redirectUrl;
        private String appId;
        private String adminKey;
    }

    @Getter
    @Setter
    public static class AppleSecret {
        private String baseUrl;
        private String teamId;
        private String logInKey;
        private String clientUrl;
        private String redirectUrl;
        private String keyPath;
    }

    // base url
    public String getKakaoBaseUrl() {
        return kakao.getBaseUrl();
    }

    // rest api key
    public String getKakaoClientId() {
        return kakao.getClientId();
    }

    // redirect url
    public String getKakaoRedirectUrl() {
        return kakao.getRedirectUrl();
    }

    // secret key
    public String getKakaoClientSecret() {
        return kakao.getClientSecret();
    }

    // native app key
    public String getKakaoAppId() {
        return kakao.getAppId();
    }

    public String getAppleBaseUrl() {
        return apple.getBaseUrl();
    }

    public String getAppleTeamId() {
        return apple.getTeamId();
    }

    public String getAppleLogInKey() {
        return apple.getLogInKey();
    }

    public String getAppleClientUrl() {
        return apple.getClientUrl();
    }

    public String getAppleRedirectUrl() {
        return apple.getRedirectUrl();
    }

    public String getAppleKeyPath() {
        return apple.getKeyPath();
    }
}
