package tify.server.api.version.model.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostVersionRequest {

    @Schema(description = "새로운 AOS 버전. 만약 AOS 버전을 변경하지 않는다면 적지 않아도 됩니다.")
    private String aosVersion;

    @Schema(description = "새로운 IOS 버전. 만약 IOS 버전을 변경하지 않는다면 적지 않아도 됩니다.")
    private String iosVersion;
}
