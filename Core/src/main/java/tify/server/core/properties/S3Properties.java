package tify.server.core.properties;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.auth.Credentials;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(prefix = "cloud.aws")
public class S3Properties {

    private Credentials credentials;
    private S3 s3;
    private Region region;

    @Getter
    @Setter
    public static class Credentials {
        private String accessKey;
        private String secretKey;
    }

    @Getter
    @Setter
    public static class S3 {
        private String bucket;
    }

    @Getter
    @Setter
    public static class Region {
        private String name;
    }

    public String getAccessKey() {
        return credentials.getAccessKey();
    }

    public String getSecretKey() {
        return credentials.getSecretKey();
    }

    public String getBucketName() {
        return s3.getBucket();
    }

    public String getRegion() {
        return region.getName();
    }
}
