package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.user.model.dto.request.UserOnBoardingRequest;
import tify.server.core.annotation.UseCase;
import tify.server.core.exception.BaseException;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.Gender;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.exception.UserException;

@UseCase
@RequiredArgsConstructor
public class UserOnBoardingUseCase {

    private final UserAdaptor userAdaptor;

    @Transactional
    public void execute(UserOnBoardingRequest body, Long userId) {
        if (userAdaptor.queryByUserId(body.getId()).isPresent()) {
            throw new BaseException(UserException.ALREADY_EXIST_USER_ERROR);
        }
        User user = userAdaptor.query(userId);
        System.out.println(" = " + body.getOnBoardingState());
        user.onBoarding(
                body.getUsername(),
                body.getId(),
                body.getBirth(),
                Gender.toGender(body.getGender()),
                userAdaptor.queryOnBoardingStatusByName(body.getOnBoardingState()));
    }

    public boolean checkUserId(String userId) {
        return !userAdaptor.queryByUserId(userId).isPresent();
    }
}
