package tify.server.core.config;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import tify.server.core.properties.JwtProperties;
import tify.server.core.properties.OauthProperties;

@EnableConfigurationProperties({JwtProperties.class, OauthProperties.class})
@Configuration
public class ConfigurationPropertiesConfig {}
