package tify.server.api.version.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VersionInfoVo {

    @Schema(description = "IOS 버전입니다.")
    private final String iosVersion;

    @Schema(description = "AOS 버전입니다.")
    private final String aosVersion;
}
