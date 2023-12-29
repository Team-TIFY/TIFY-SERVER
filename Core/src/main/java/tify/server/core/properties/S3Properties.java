package tify.server.core.properties;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "s3")
public class S3Properties {

    private String accessKey;

    private String secretKey;

    private String region;

    private String bucketName;
}
