package tify.server.infrastructure.outer.api.oauth.config;


import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Import(AppleAuthErrorDecoder.class)
public class AppleAuthFeignConfig {

    @Bean
    @ConditionalOnMissingBean(value = ErrorDecoder.class)
    public AppleAuthErrorDecoder commonFeignErrorDecoder() {
        return new AppleAuthErrorDecoder();
    }

    @Bean
    Encoder formEncoder() {
        return new feign.form.FormEncoder();
    }
}
