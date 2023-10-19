package tify.server.api.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import tify.server.api.common.slice.SliceResponse;
import tify.server.api.user.model.dto.request.CreateNeighborUseCase;
import tify.server.api.user.model.dto.request.PatchNeighborsOrdersRequest;
import tify.server.api.user.model.dto.request.PutUserProfileRequest;
import tify.server.api.user.model.dto.request.UserOnBoardingRequest;
import tify.server.api.user.model.dto.response.OnBoardingStatusResponse;
import tify.server.api.user.service.*;
import tify.server.domain.common.vo.UserFavorVo;
import tify.server.domain.common.vo.UserInfoVo;
import tify.server.domain.common.vo.UserProfileVo;
import tify.server.domain.common.vo.UserTagVo;
import tify.server.domain.domains.user.domain.LargeCategory;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborApplicationDTO;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborDTO;

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
    private final RetrieveNeighborListUseCase retrieveNeighborListUseCase;
    private final UpdateNeighborUseCase updateNeighborUseCase;
    private final CreateNeighborUseCase createNeighborUseCase;
    private final RetrieveBirthdayNeighborUseCase retrieveBirthdayNeighborUseCase;
    private final RetrieveNeighborApplicationUseCase retrieveNeighborApplicationUseCase;

    // userId를 pathvariable로 받아서 그 해당 유저의 profile 정보를 리턴하기.
    @Operation(summary = "유저 정보 조회")
    @GetMapping("/{userId}")
    public UserProfileVo getUserProfileInfo(@PathVariable Long userId) {
        return userInfoUseCase.execute(userId);
    }

    @Operation(summary = "친구 정보 조회")
    @GetMapping("/neighbors/{neighborId}")
    public UserProfileVo getNeighborProfileInfo(@PathVariable Long neighborId) {
        return neighborInfoUseCase.execute(neighborId);
    }

    @Operation(summary = "친구 정보 조회 시간 업데이트")
    @PatchMapping("/neighbors/{neighborId}")
    public void updateNeighborViewedAt(@PathVariable Long neighborId) {
        neighborInfoUseCase.updateViewedAt(neighborId);
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

    @Operation(summary = "친구 목록 조회")
    @GetMapping("/neighbors")
    public SliceResponse<RetrieveNeighborDTO> getNeighbors(
            @ParameterObject @PageableDefault(size = 10) Pageable pageable) {
        return retrieveNeighborListUseCase.execute(pageable);
    }

    @Operation(summary = "친구 목록 순서 수정")
    @PatchMapping("/neighbors/orders")
    public void patchNeighborsOrders(@RequestBody @Valid PatchNeighborsOrdersRequest body) {
        updateNeighborUseCase.executeToOrder(body.getFromNeighborId(), body.getToNeighborId());
    }

    @Operation(summary = "친구 목록 보여짐 여부 수정")
    @PatchMapping("/neighbors/{neighborId}/views")
    public void patchNeighborIsViews(@PathVariable Long neighborId) {
        updateNeighborUseCase.executeToIsView(neighborId);
    }

    @Operation(summary = "친구 신청")
    @PostMapping("{toUserId}/neighbors")
    public void postNeighbor(@PathVariable Long toUserId) {
        createNeighborUseCase.execute(toUserId);
    }

    @Operation(summary = "생일인 친구 조회")
    @GetMapping("/neighbors/birthday")
    public SliceResponse<RetrieveNeighborDTO> getBirthdayNeighbor(
            @ParameterObject @PageableDefault Pageable pageable) {
        return retrieveBirthdayNeighborUseCase.execute(pageable);
    }

    @Operation(summary = "친구 신청 목록을 조회합니다.")
    @GetMapping("/{toUserId}/neighbors/applications")
    public SliceResponse<RetrieveNeighborApplicationDTO> getNeighborApplications(
            @ParameterObject @PageableDefault Pageable pageable, @PathVariable Long toUserId) {
        return SliceResponse.of(retrieveNeighborApplicationUseCase.execute(pageable, toUserId));
    }
}
