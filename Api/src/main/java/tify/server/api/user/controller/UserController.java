package tify.server.api.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tify.server.api.user.service.UserFavorUseCase;
import tify.server.api.user.service.UserInfoUseCase;
import tify.server.domain.common.vo.UserProfileVo;
import tify.server.domain.common.vo.UserTagVo;

@SecurityRequirement(name = "access-token")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "1 [유저]")
public class UserController {

    private final UserInfoUseCase userInfoUseCase;
    private final UserFavorUseCase userFavorUseCase;

    // userId를 pathvariable로 받아서 그 해당 유저의 profile 정보를 리턴하기.
    @Operation(summary = "유저 정보 조회")
    @GetMapping("/{userId}")
    public UserProfileVo getUserProfileInfo(@PathVariable Long userId) {
        return userInfoUseCase.execute(userId);
    }

    //    @Operation(summary = "내 취향 조회")
    //    @GetMapping("/me/favor")
    //    public UserFavorVo getMyUserFavor() {
    //        return userFavorUseCase.execute();
    //    }

    @Operation(summary = "내 취향 태그 조회")
    @GetMapping("/{userId}/tags")
    public List<UserTagVo> getUserTags(@PathVariable Long userId) {
        return userFavorUseCase.execute(userId);
    }
}
