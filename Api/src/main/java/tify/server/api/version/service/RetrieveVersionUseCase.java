package tify.server.api.version.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.version.model.vo.VersionInfoVo;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.version.adaptor.VersionAdaptor;
import tify.server.domain.domains.version.domain.Version;

@UseCase
@RequiredArgsConstructor
public class RetrieveVersionUseCase {

    private final VersionAdaptor versionAdaptor;

    @Transactional(readOnly = true)
    public VersionInfoVo execute() {
        Version version = versionAdaptor.queryRecentVersion();
        return VersionInfoVo.builder()
                .iosVersion(version.getIosVersion())
                .aosVersion(version.getAosVersion())
                .build();
    }
}
