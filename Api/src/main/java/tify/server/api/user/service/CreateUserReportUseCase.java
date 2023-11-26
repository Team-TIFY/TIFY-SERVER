package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
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
    public void execute(Long userId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        userReportAdaptor.save(
                UserReport.builder()
                        .fromUserId(currentUserId)
                        .toUserId(userAdaptor.query(userId).getId())
                        .build());
    }
}
