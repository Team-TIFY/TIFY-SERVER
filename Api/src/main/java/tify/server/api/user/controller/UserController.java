package tify.server.api.user.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tify.server.api.common.slice.SliceResponse;
import tify.server.api.user.model.dto.request.PatchExpoTokenRequest;
import tify.server.api.user.model.dto.request.PatchNeighborsOrdersRequest;
import tify.server.api.user.model.dto.request.PatchUserFavorRequest;
import tify.server.api.user.model.dto.request.PatchUserProfileRequest;
import tify.server.api.user.model.dto.request.PostUserOpinionRequest;
import tify.server.api.user.model.dto.request.UserOnBoardingRequest;
import tify.server.api.user.model.dto.response.OnBoardingStatusResponse;
import tify.server.api.user.model.dto.response.UserReportResponse;
import tify.server.api.user.model.dto.vo.MutualFriendsVo;
import tify.server.api.user.model.dto.vo.MyDailyQuestionAnswerVo;
import tify.server.api.user.model.dto.vo.RetrieveUserFavorBoxVo;
import tify.server.api.user.model.dto.vo.UserDailyQuestionAnswerVo;
import tify.server.api.user.model.dto.vo.UserFavorBoxVo;
import tify.server.api.user.model.dto.vo.UserOpinionVo;
import tify.server.api.user.model.dto.vo.UserReportInfoVo;
import tify.server.api.user.model.dto.vo.UserSearchInfoVo;
import tify.server.api.user.service.AcceptanceNeighborApplicationUseCase;
import tify.server.api.user.service.CreateNeighborUseCase;
import tify.server.api.user.service.CreateUserOpinionUseCase;
import tify.server.api.user.service.CreateUserReportUseCase;
import tify.server.api.user.service.NeighborInfoUseCase;
import tify.server.api.user.service.RejectNeighborApplicationUseCase;
import tify.server.api.user.service.RemoveNeighborUseCase;
import tify.server.api.user.service.RetrieveBirthdayNeighborUseCase;
import tify.server.api.user.service.RetrieveMutualFriendsUseCase;
import tify.server.api.user.service.RetrieveMyDailyAnswerUseCase;
import tify.server.api.user.service.RetrieveNeighborApplicationUseCase;
import tify.server.api.user.service.RetrieveNeighborFavorBoxUseCase;
import tify.server.api.user.service.RetrieveNeighborListUseCase;
import tify.server.api.user.service.RetrieveUserListUseCase;
import tify.server.api.user.service.RetrieveUserOpinionUseCase;
import tify.server.api.user.service.RetrieveUserReportUseCase;
import tify.server.api.user.service.UpdateNeighborUseCase;
import tify.server.api.user.service.UpdateUserExpoTokenUseCase;
import tify.server.api.user.service.UpdateUserFavorUseCase;
import tify.server.api.user.service.UpdateUserProfileUseCase;
import tify.server.api.user.service.UserBlockUseCase;
import tify.server.api.user.service.UserFavorFilterUseCase;
import tify.server.api.user.service.UserFavorUseCase;
import tify.server.api.user.service.UserInfoUseCase;
import tify.server.api.user.service.UserOnBoardingUseCase;
import tify.server.domain.domains.question.domain.DailyQuestionCategory;
import tify.server.domain.domains.user.domain.SmallCategory;
import tify.server.domain.domains.user.dto.condition.UserCondition;
import tify.server.domain.domains.user.dto.model.GetNeighborApplicationDTO;
import tify.server.domain.domains.user.dto.model.RetrieveNeighborDTO;
import tify.server.domain.domains.user.vo.UserAnswerVo;
import tify.server.domain.domains.user.vo.UserInfoVo;
import tify.server.domain.domains.user.vo.UserProfileVo;

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
    private final UpdateUserFavorUseCase updateUserFavorUseCase;
    private final UserOnBoardingUseCase userOnBoardingUseCase;
    private final NeighborInfoUseCase neighborInfoUseCase;
    private final RetrieveNeighborListUseCase retrieveNeighborListUseCase;
    private final UpdateNeighborUseCase updateNeighborUseCase;
    private final CreateNeighborUseCase createNeighborUseCase;
    private final RetrieveBirthdayNeighborUseCase retrieveBirthdayNeighborUseCase;
    private final RetrieveNeighborApplicationUseCase retrieveNeighborApplicationUseCase;
    private final AcceptanceNeighborApplicationUseCase acceptanceNeighborApplicationUseCase;
    private final RejectNeighborApplicationUseCase rejectNeighborApplicationUseCase;
    private final RetrieveUserListUseCase retrieveUserListUseCase;
    private final RetrieveMutualFriendsUseCase retrieveMutualFriendsUseCase;
    private final RemoveNeighborUseCase removeNeighborUseCase;
    private final UserBlockUseCase userBlockUseCase;
    private final CreateUserReportUseCase createUserReportUseCase;
    private final RetrieveUserReportUseCase retrieveUserReportUseCase;
    private final RetrieveMyDailyAnswerUseCase retrieveMyDailyAnswerUseCase;
    private final CreateUserOpinionUseCase createUserOpinionUseCase;
    private final RetrieveUserOpinionUseCase retrieveUserOpinionUseCase;
    private final RetrieveNeighborFavorBoxUseCase retrieveNeighborFavorBoxUseCase;
    private final UpdateUserExpoTokenUseCase updateUserExpoTokenUseCase;

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

    @Operation(summary = "유저 취향 답변 중분류 별 조회")
    @GetMapping("/{userId}/favors")
    public List<UserAnswerVo> getUserTags(
            @PathVariable Long userId,
            @RequestParam @Parameter(description = "필터로 쓰일 중분류입니다.")
                    List<SmallCategory> smallCategory) {

        return userFavorUseCase.execute(userId, smallCategory);
    }

    @Operation(summary = "유저 정보 수정")
    @PatchMapping("/profile")
    public void patchUserProfile(@RequestBody PatchUserProfileRequest body) {
        updateUserProfileUseCase.execute(body);
    }

    @Operation(summary = "유저 정보 조회 (토큰)")
    @GetMapping("/me")
    public UserInfoVo getUserProfileInfoByToken() {
        return userInfoUseCase.executeByToken();
    }

    @Operation(summary = "유저 취향 상자 조회")
    @GetMapping("/{userId}/tags")
    public List<UserFavorBoxVo> getUserFavorsByUserId(@PathVariable Long userId) {
        return userFavorFilterUseCase.execute(userId);
    }

    @Operation(summary = "유저 취향 상자 수정")
    @PatchMapping("/{userId}/tags")
    public void patchUserFavorsByUserId(
            @PathVariable Long userId,
            @RequestBody @Valid PatchUserFavorRequest patchUserFavorRequest) {
        updateUserFavorUseCase.execute(userId, patchUserFavorRequest);
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
    public List<RetrieveNeighborDTO> getNeighbors() {
        return retrieveNeighborListUseCase.execute();
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
    public List<RetrieveNeighborDTO> getBirthdayNeighbor() {
        return retrieveBirthdayNeighborUseCase.execute();
    }

    @Operation(summary = "친구 신청 목록을 조회합니다.")
    @GetMapping("/neighbors/applications")
    public SliceResponse<GetNeighborApplicationDTO> getNeighborApplications(
            @ParameterObject @PageableDefault Pageable pageable) {
        return SliceResponse.of(retrieveNeighborApplicationUseCase.execute(pageable));
    }

    @Operation(summary = "친구 신청을 수락합니다.")
    @PatchMapping("/neighbors/applications/{neighborApplicationId}/accept")
    public void patchNeighborApplicationAccept(@PathVariable Long neighborApplicationId) {
        acceptanceNeighborApplicationUseCase.execute(neighborApplicationId);
    }

    @Operation(summary = "친구 신청을 거절합니다.")
    @PatchMapping("/neighbors/applications/{neighborApplicationId}/reject")
    public void patchNeighborApplicationReject(@PathVariable Long neighborApplicationId) {
        rejectNeighborApplicationUseCase.execute(neighborApplicationId);
    }

    @Operation(summary = "유저를 검색합니다.")
    @GetMapping
    public SliceResponse<UserSearchInfoVo> getUsers(
            @ParameterObject @PageableDefault Pageable pageable,
            @ParameterObject UserCondition condition) {
        return SliceResponse.of(retrieveUserListUseCase.execute(pageable, condition));
    }

    @Operation(summary = "함께 아는 친구의 수를 조회합니다.")
    @GetMapping("/neighbors/mutual/{toUserId}")
    public MutualFriendsVo getMutualFriendNum(@PathVariable Long toUserId) {
        return retrieveMutualFriendsUseCase.execute(toUserId);
    }

    @Operation(summary = "친구를 삭제합니다.")
    @DeleteMapping("/{toUserId}/neighbors/delete")
    public void postNeighborDelete(@PathVariable Long toUserId) {
        removeNeighborUseCase.execute(toUserId);
    }

    @Operation(summary = "유저를 차단합니다.")
    @PostMapping("/{toUserId}/block")
    public void postUserBlock(@PathVariable Long toUserId) {
        userBlockUseCase.execute(toUserId);
    }

    @Operation(summary = "차단된 유저의 차단을 해제합니다.")
    @DeleteMapping("/{toUserId}/block")
    public void deleteUserBlock(@PathVariable Long toUserId) {
        userBlockUseCase.delete(toUserId);
    }

    @Operation(summary = "유저를 신고합니다.")
    @PostMapping("/report/{userId}")
    public UserReportResponse postUserReport(@PathVariable Long userId) {
        return createUserReportUseCase.execute(userId);
    }

    @Operation(summary = "유저가 당한 신고의 정보를 조회합니다.")
    @GetMapping("/report/{userId}")
    public UserReportInfoVo getUserReport(@PathVariable Long userId) {
        return retrieveUserReportUseCase.execute(userId);
    }

    @Operation(summary = "유저가 답변한 데일리 질문의 갯수를 모든 카테고리별로 조회합니다.")
    @GetMapping("/daily-answer/{userId}/count/all-category")
    public List<UserDailyQuestionAnswerVo> getMyAllDailyQuestionCountList(
            @PathVariable Long userId) {
        return retrieveMyDailyAnswerUseCase.countByAllCategory(userId);
    }

    @Operation(summary = "유저가 답변한 데일리 질문 갯수를 카테고리별로 조회합니다.")
    @GetMapping("/daily-answer/{userId}/count")
    public Long getMyDailyQuestionCountList(
            @PathVariable Long userId, @RequestParam DailyQuestionCategory dailyQuestionCategory) {
        return retrieveMyDailyAnswerUseCase.countByCategory(userId, dailyQuestionCategory);
    }

    @Operation(summary = "유저가 답변한 데일리 질문에 대한 답변을 월별로 카테고리를 이용해 조회합니다.")
    @GetMapping("/daily-answer/{userId}")
    public List<List<MyDailyQuestionAnswerVo>> getMyDailyAnswerList(
            @PathVariable Long userId, @RequestParam DailyQuestionCategory dailyQuestionCategory) {

        return retrieveMyDailyAnswerUseCase.execute(userId, dailyQuestionCategory);
    }

    @Operation(summary = "새로운 친구 목록을 조회합니다.")
    @GetMapping("neighbors/isNew")
    public List<RetrieveNeighborDTO> getNewNeighborList() {
        return retrieveNeighborListUseCase.executeToIsNew();
    }

    @Operation(summary = "새로운 친구를 확인하고 표시하지 않게 합니다.")
    @PatchMapping("/neighbors/{userId}/isNew")
    public void patchNeighborIsNew(@PathVariable Long userId) {
        updateNeighborUseCase.executeToIsNew(userId);
    }

    @Operation(summary = "운영진에 의견을 보냅니다.")
    @PostMapping("/opinion/new")
    public void postUserOpinion(@RequestBody PostUserOpinionRequest postUserOpinionRequest) {
        createUserOpinionUseCase.execute(postUserOpinionRequest);
    }

    @Operation(summary = "내가 보낸 의견을 조회합니다.")
    @GetMapping("/opinion")
    public UserOpinionVo getMyOpinion(
            @RequestParam @Parameter(description = "문의의 pk값입니다.") Long opinionId) {

        return retrieveUserOpinionUseCase.execute(opinionId);
    }

    @Operation(summary = "내가 보낸 의견을 모두 조회합니다.")
    @GetMapping("/opinion/all")
    public List<UserOpinionVo> getMyAllOpinion() {
        return retrieveUserOpinionUseCase.executeAll();
    }

    @Operation(summary = "친구들의 취향 상자 정보와 프로필 리스트를 조회합니다.")
    @GetMapping("/neighbors/favors")
    public List<RetrieveUserFavorBoxVo> getNeighborsFavorBox() {
        return retrieveNeighborFavorBoxUseCase.execute();
    }

    @Operation(summary = "유저의 expo 토큰을 업데이트합니다.")
    @PatchMapping("/expo-token")
    public void patchExpoToken(
            @RequestParam Long userId, @RequestBody @Valid PatchExpoTokenRequest request) {
        updateUserExpoTokenUseCase.execute(userId, request);
    }
}
