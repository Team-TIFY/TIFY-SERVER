package tify.server.infrastructure.config.redis;


import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableRedisRepositories(
        basePackages = "tify.server",
        enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_STARTUP)
@Configuration
public class RedisConfig {

    @Value("${REDIS_HOST}")
    private String host;

    @Value("${REDIS_PORT}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration(host, port);

        //        if (redisPassword != null && !redisPassword.isBlank())
        //            redisConfig.setPassword(redisPassword);

        LettuceClientConfiguration clientConfig =
                LettuceClientConfiguration.builder()
                        .commandTimeout(Duration.ofSeconds(1))
                        .shutdownTimeout(Duration.ZERO)
                        .build();
        return new LettuceConnectionFactory(redisConfig, clientConfig);
    }
}
