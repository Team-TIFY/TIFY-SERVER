package tify.server.api.user.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.api.user.model.dto.request.UserReportRequest;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.UserReportAdaptor;
import tify.server.domain.domains.user.domain.UserReport;
import tify.server.domain.domains.user.validator.UserValidator;

@UseCase
@RequiredArgsConstructor
public class CreateUserReportUseCase {

    private final UserReportAdaptor userReportAdaptor;
    private final UserValidator userValidator;

    @Transactional
    public void execute(Long userId, UserReportRequest body) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        userValidator.isValidUserId(body.getUserId());
        userReportAdaptor.save(
                UserReport.builder()
                        .fromUserId(currentUserId)
                        .toUserId(userId)
                        .title(body.getTitle())
                        .content(body.getContent())
                        .build());
    }
}
