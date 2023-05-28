package tify.server.api.user.controller;


import tify.server.api.user.model.response.UserInfoResponse;
import tify.server.api.user.service.UserInfoUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SecurityRequirement(name = "access-token")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "1.3 [유저]")
public class UserController {

    private final UserInfoUseCase userInfoUseCase;

    @Operation(summary = "내 정보 조회")
    @GetMapping(produces = "application/json; charset=utf-8")
    public UserInfoResponse getUserInfo() {
        return userInfoUseCase.execute();
    }
}
