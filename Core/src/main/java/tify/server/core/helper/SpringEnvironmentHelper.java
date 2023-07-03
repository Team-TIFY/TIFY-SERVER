package tify.server.core.helper;


import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import tify.server.core.annotation.Helper;

@Helper
@RequiredArgsConstructor
public class SpringEnvironmentHelper {

    private final Environment environment;

    private static final String PROD = "prod";

    private static final String DEV = "dev";

    private static final String LOCAL = "local";

    private static List<String> PROD_AND_DEV = List.of(PROD, DEV);

    public Boolean isProdProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> currentProfile = Arrays.stream(activeProfiles).toList();
        return currentProfile.contains(PROD);
    }

    public Boolean isDevProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> currentProfile = Arrays.stream(activeProfiles).toList();
        return currentProfile.contains(DEV);
    }

    public Boolean isLocalProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> currentProfile = Arrays.stream(activeProfiles).toList();
        return currentProfile.contains(LOCAL);
    }

    public Boolean isProdOrDevProfile() {
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> currentProfile = Arrays.stream(activeProfiles).toList();
        return CollectionUtils.containsAny(PROD_AND_DEV, currentProfile);
    }
}
