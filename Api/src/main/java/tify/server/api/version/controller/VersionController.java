package tify.server.api.version.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tify.server.api.version.model.request.PostVersionRequest;
import tify.server.api.version.model.vo.VersionInfoVo;
import tify.server.api.version.service.CreateVersionUseCase;
import tify.server.api.version.service.RetrieveVersionUseCase;
import tify.server.api.version.service.UpdateVersionUseCase;

@SecurityRequirement(name = "access-token")
@RestController
@RequestMapping("/version")
@RequiredArgsConstructor
@Tag(name = "7. [버전]")
public class VersionController {

    private final RetrieveVersionUseCase retrieveVersionUseCase;
    private final CreateVersionUseCase createVersionUseCase;
    private final UpdateVersionUseCase updateVersionUseCase;

    @Operation(summary = "현재 버전을 조회합니다. (AOS, IOS 모두)")
    @GetMapping
    public VersionInfoVo getRecentVersion() {
        return retrieveVersionUseCase.execute();
    }

    @Operation(summary = "초기 버전을 저장합니다. (추후 버전 업데이트 시 업데이트 api 이용)")
    @PostMapping("/new")
    public void createVersion(@RequestBody PostVersionRequest postVersionRequest) {
        createVersionUseCase.execute(postVersionRequest);
    }

    @Operation(summary = "버전을 업데이트합니다.")
    @PostMapping("/update")
    public void updateVersion(@RequestBody PostVersionRequest postVersionRequest) {
        updateVersionUseCase.execute(postVersionRequest);
    }
}
