package tify.server.api.version.service;


import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.version.model.request.PostVersionRequest;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.version.adaptor.VersionAdaptor;
import tify.server.domain.domains.version.domain.Version;

@UseCase
@RequiredArgsConstructor
public class UpdateVersionUseCase {

    private final VersionAdaptor versionAdaptor;

    @Transactional
    public void execute(PostVersionRequest postVersionRequest) {
        Version recentVersion = versionAdaptor.queryRecentVersion();
        Optional.ofNullable(postVersionRequest.getAosVersion())
                .ifPresent(recentVersion::updateAosVersion);
        Optional.ofNullable(postVersionRequest.getIosVersion())
                .ifPresent(recentVersion::updateIosVersion);
    }
}
