package tify.server.api.user.service;


import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.user.model.dto.request.UserOnBoardingRequest;
import tify.server.core.annotation.UseCase;
import tify.server.core.exception.BaseException;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.adaptor.UserFavorAdaptor;
import tify.server.domain.domains.user.domain.Gender;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserFavor;
import tify.server.domain.domains.user.exception.UserException;
import tify.server.domain.domains.user.vo.UserOnBoardingStatusInfoVo;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserOnBoardingUseCase {

    private final UserAdaptor userAdaptor;
    private final UserFavorAdaptor userFavorAdaptor;

    @Transactional
    public void execute(UserOnBoardingRequest body, Long userId) {
        Optional<User> getUser = userAdaptor.queryByUserId(body.getId());
        if (getUser.isPresent() && !getUser.get().getId().equals(userId)) {
            throw new BaseException(UserException.ALREADY_EXIST_USER_ERROR);
        }
        User user = userAdaptor.query(userId);

        if (body.getUserFavorDtoList().size() != 3) {
            throw new BaseException(UserException.USER_FAVOR_UPDATE_ERROR);
        }

        body.getUserFavorDtoList()
                .forEach(
                        detailCategory -> {
                            UserFavor userFavor =
                                    UserFavor.builder()
                                            .user(user)
                                            .detailCategory(detailCategory)
                                            .build();
                            userFavorAdaptor.save(userFavor);
                        });

        List<UserFavor> userFavorList = userFavorAdaptor.queryAllByUser(user);

        user.onBoarding(
                body.getUsername(),
                body.getId(),
                body.getBirth(),
                Gender.toGender(body.getGender()),
                userAdaptor.queryOnBoardingStatusByName(body.getOnBoardingState()),
                userFavorList);
    }

    public boolean checkUserId(String userId) {
        return !userAdaptor.queryByUserId(userId).isPresent();
    }

    public List<UserOnBoardingStatusInfoVo> retrieveOnBoardingStatuses(String keyword) {
        if (keyword != null && (keyword.equals(",") || keyword.equals(" "))) {
            throw new BaseException(UserException.ON_BOARDING_STATUS_FILTER_ERROR);
        }
        return userAdaptor.searchByKeyword(keyword);
    }
}
