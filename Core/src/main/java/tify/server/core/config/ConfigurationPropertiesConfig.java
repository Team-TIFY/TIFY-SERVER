package tify.server.core.config;


import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import tify.server.core.properties.JwtProperties;
import tify.server.core.properties.OauthProperties;
import tify.server.core.properties.S3Properties;

@EnableConfigurationProperties({JwtProperties.class, OauthProperties.class, S3Properties.class})
@Configuration
public class ConfigurationPropertiesConfig {}
