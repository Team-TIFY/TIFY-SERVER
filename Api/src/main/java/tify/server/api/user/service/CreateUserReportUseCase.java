package tify.server.api.user.service;

import static org.springframework.http.HttpStatus.*;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.api.user.model.dto.response.UserReportResponse;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.adaptor.UserReportAdaptor;
import tify.server.domain.domains.user.domain.UserReport;
import tify.server.domain.domains.user.validator.UserValidator;

@UseCase
@RequiredArgsConstructor
public class CreateUserReportUseCase {

    private final UserReportAdaptor userReportAdaptor;
    private final UserAdaptor userAdaptor;
    private final UserValidator userValidator;

    @Transactional
    public UserReportResponse execute(Long userId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        userValidator.isResignedUser(userId);
        Optional<UserReport> report =
                userReportAdaptor.optionalQueryByFromUserIdAndToUserId(currentUserId, userId);
        if (report.isPresent()) { // 이미 신고가 존재할 때
            return UserReportResponse.builder()
                    .fromUserId(currentUserId)
                    .toUserId(userId)
                    .reportSuccess(false)
                    .build();
        } else { // 새로운 신고일 때
            userReportAdaptor.save(
                    UserReport.builder()
                            .fromUserId(currentUserId)
                            .toUserId(userAdaptor.query(userId).getId())
                            .build());
            return UserReportResponse.builder()
                    .fromUserId(currentUserId)
                    .toUserId(userId)
                    .reportSuccess(true)
                    .build();
        }
    }
}
