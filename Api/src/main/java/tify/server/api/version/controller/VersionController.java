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

@SecurityRequirement(name = "access-token")
@RestController
@RequestMapping("/version")
@RequiredArgsConstructor
@Tag(name = "7. [버전]")
public class VersionController {

    private final RetrieveVersionUseCase retrieveVersionUseCase;
    private final CreateVersionUseCase createVersionUseCase;

    @Operation(summary = "현재 버전을 조회합니다. (AOS, IOS 모두)")
    @GetMapping
    public VersionInfoVo getRecentVersion() {
        return retrieveVersionUseCase.execute();
    }

    @Operation(summary = "새로운 버전을 저장합니다.")
    @PostMapping("/new")
    public void updateAosVersion(@RequestBody PostVersionRequest postVersionRequest) {
        createVersionUseCase.execute(postVersionRequest);
    }
}
