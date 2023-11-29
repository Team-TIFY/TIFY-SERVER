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
public class CreateVersionUseCase {

    private final VersionAdaptor versionAdaptor;

    @Transactional
    public void execute(PostVersionRequest postVersionRequest) {
        Version version = new Version(null, null);
        Optional.ofNullable(postVersionRequest.getAosVersion())
                .ifPresent(version::updateAosVersion);
        Optional.ofNullable(postVersionRequest.getIosVersion())
                .ifPresent(version::updateIosVersion);
        versionAdaptor.save(version);
    }
}
