package tify.server.domain.domains.version.adaptor;


import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.version.domain.Version;
import tify.server.domain.domains.version.exception.VersionNotFoundException;
import tify.server.domain.domains.version.repository.VersionRepository;

@Adaptor
@RequiredArgsConstructor
public class VersionAdaptor {

    private final VersionRepository versionRepository;

    public Version queryRecentVersion() {
        return versionRepository.findById(1L).orElseThrow(() -> VersionNotFoundException.EXCEPTION);
    }

    public void save(Version version) {
        versionRepository.save(version);
    }
}
