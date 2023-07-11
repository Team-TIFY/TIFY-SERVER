package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.user.model.dto.request.PutUserProfileRequest;
import tify.server.api.utils.UserUtils;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.domain.Gender;
import tify.server.domain.domains.user.domain.User;

@UseCase
@RequiredArgsConstructor
@Transactional
public class UpdateUserProfileUseCase {

    private final UserUtils userUtils;

    public void execute(PutUserProfileRequest body) {
        // 현재 로그인된 유저 조회
        User currentUser = userUtils.getUser();
        // 입력 오류 검증
        Gender gender = Gender.toGender(body.getGender());
        // 유저 정보 업데이트
        currentUser
                .getProfile()
                .updateProfile(
                        body.getUsername(),
                        body.getThumbnail(),
                        body.getBirth(),
                        body.getJob(),
                        gender);
    }
}
