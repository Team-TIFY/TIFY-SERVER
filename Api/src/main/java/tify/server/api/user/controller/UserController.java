package tify.server.api.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tify.server.api.user.model.dto.request.PutUserProfileRequest;
import tify.server.api.user.model.dto.request.UserOnBoardingRequest;
import tify.server.api.user.model.dto.response.OnBoardingStatusResponse;
import tify.server.api.user.service.*;
import tify.server.domain.common.vo.UserFavorVo;
import tify.server.domain.common.vo.UserInfoVo;
import tify.server.domain.common.vo.UserProfileVo;
import tify.server.domain.common.vo.UserTagVo;
import tify.server.domain.domains.user.domain.LargeCategory;

@SecurityRequirement(name = "access-token")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "2. [유저]")
public class UserController {

    private final UserInfoUseCase userInfoUseCase;
    private final UserFavorUseCase userFavorUseCase;
    private final UpdateUserProfileUseCase updateUserProfileUseCase;
    private final UserFavorFilterUseCase userFavorFilterUseCase;
    private final UserOnBoardingUseCase userOnBoardingUseCase;
    private final NeighborInfoUseCase neighborInfoUseCase;

    // userId를 pathvariable로 받아서 그 해당 유저의 profile 정보를 리턴하기.
    @Operation(summary = "유저 정보 조회")
    @GetMapping("/{userId}")
    public UserProfileVo getUserProfileInfo(@PathVariable Long userId) {
        return userInfoUseCase.execute(userId);
    }

    @Operation(summary = "친구 정보 조회")
    @GetMapping("/{neighborId}")
    public UserProfileVo getNeighborProfileInfo(@PathVariable Long neighborId) {
        return neighborInfoUseCase.execute(neighborId);
    }

    @Operation(summary = "내 취향 태그 조회")
    @GetMapping("/{userId}/tags")
    public List<UserTagVo> getUserTags(@PathVariable Long userId) {
        return userFavorUseCase.execute(userId);
    }

    @Deprecated
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

    @Operation(summary = "대분류 별 유저 취향 조회 필터")
    @GetMapping("/{userId}/category")
    public List<UserFavorVo> getUserTagsByLargeCategory(
            @PathVariable Long userId, @RequestParam LargeCategory largeCategory) {
        return userFavorFilterUseCase.execute(userId, largeCategory);
    }

    @Operation(summary = "온보딩")
    @PutMapping("/{userId}")
    public void userOnBoarding(
            @RequestBody @Valid UserOnBoardingRequest body, @PathVariable Long userId) {
        userOnBoardingUseCase.execute(body, userId);
    }

    @Operation(summary = "유저 id 중복 검사")
    @GetMapping("/id/check")
    public boolean getValidUserId(@RequestParam String id) {
        return userOnBoardingUseCase.checkUserId(id);
    }

    @Operation(summary = "온보딩 상태 값 필터 조회")
    @GetMapping("/onBoardingStatus")
    public OnBoardingStatusResponse getOnBoardingStatus(
            @RequestParam(required = false) String keyword) {
        return OnBoardingStatusResponse.from(
                userOnBoardingUseCase.retrieveOnBoardingStatuses(keyword));
    }
}
