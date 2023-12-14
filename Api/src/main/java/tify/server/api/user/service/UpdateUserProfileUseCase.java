package tify.server.api.user.service;


import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.user.model.dto.request.PatchUserProfileRequest;
import tify.server.api.utils.UserUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.UserOnBoardingStatusAdaptor;
import tify.server.domain.domains.user.domain.Gender;
import tify.server.domain.domains.user.domain.Profile;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserOnBoardingStatus;

@UseCase
@RequiredArgsConstructor
public class UpdateUserProfileUseCase {

    private final UserUtils userUtils;
    private final UserOnBoardingStatusAdaptor userOnBoardingStatusAdaptor;

    @Transactional
    public void execute(PatchUserProfileRequest body) {
        User currentUser = userUtils.getUser();

        Gender gender =
                Gender.valueOf(
                        Optional.ofNullable(body.getGender())
                                .orElse(String.valueOf(currentUser.getProfile().getGender())));

        UserOnBoardingStatus status =
                userOnBoardingStatusAdaptor.queryByName(
                        Optional.ofNullable(body.getOnBoardingStatus())
                                .orElse(currentUser.getOnBoardingStatus().getName()));

        Profile newProfile =
                Profile.builder()
                        .userName(
                                Optional.ofNullable(body.getUsername())
                                        .orElse(currentUser.getProfile().getUserName()))
                        .birth(
                                Optional.ofNullable(body.getBirth())
                                        .orElse(currentUser.getProfile().getBirth()))
                        .job(
                                Optional.ofNullable(body.getJob())
                                        .orElse(currentUser.getProfile().getJob()))
                        .thumbNail(
                                Optional.ofNullable(body.getThumbnail())
                                        .orElse(currentUser.getProfile().getThumbNail()))
                        .gender(gender)
                        .build();

        currentUser.updateProfile(newProfile);

        currentUser.updateUserId(
                Optional.ofNullable(body.getUserId()).orElse(currentUser.getUserId()));

        currentUser.updateOnBoardingStatus(status);
    }
}
