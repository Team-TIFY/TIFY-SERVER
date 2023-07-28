package tify.server.api.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tify.server.api.user.model.dto.request.PutUserProfileRequest;
import tify.server.api.user.service.UpdateUserProfileUseCase;
import tify.server.api.user.service.UserFavorUseCase;
import tify.server.api.user.service.UserInfoUseCase;
import tify.server.domain.common.vo.UserInfoVo;
import tify.server.domain.common.vo.UserProfileVo;
import tify.server.domain.common.vo.UserTagVo;

@SecurityRequirement(name = "access-token")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "2. [유저]")
public class UserController {

    private final UserInfoUseCase userInfoUseCase;
    private final UserFavorUseCase userFavorUseCase;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;

    // userId를 pathvariable로 받아서 그 해당 유저의 profile 정보를 리턴하기.
    @Operation(summary = "유저 정보 조회")
    @GetMapping("/{userId}")
    public UserProfileVo getUserProfileInfo(@PathVariable Long userId) {
        return userInfoUseCase.execute(userId);
    }

    @Operation(summary = "내 취향 태그 조회")
    @GetMapping("/{userId}/tags")
    public List<UserTagVo> getUserTags(@PathVariable Long userId) {
        return userFavorUseCase.execute(userId);
    }

    @Operation(summary = "유저 정보 수정")
    @PutMapping("/profile")
    public void putUserProfile(@RequestBody @Valid PutUserProfileRequest body) {
        updateUserProfileUseCase.execute(body);
    }

    @Operation(summary = "유저 정보 조회 (토큰)")
    @GetMapping
    public UserInfoVo getUserProfileInfoByToken() {
        return userInfoUseCase.executeByToken();
    }
}
