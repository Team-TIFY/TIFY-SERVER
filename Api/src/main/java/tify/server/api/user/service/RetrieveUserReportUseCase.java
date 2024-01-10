package tify.server.api.user.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.user.model.dto.vo.UserReportInfoVo;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.adaptor.UserReportAdaptor;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserReport;
import tify.server.domain.domains.user.validator.UserValidator;

@UseCase
@RequiredArgsConstructor
public class RetrieveUserReportUseCase {

    private final UserAdaptor userAdaptor;
    private final UserReportAdaptor userReportAdaptor;
    private final UserValidator userValidator;

    @Transactional(readOnly = true)
    public UserReportInfoVo execute(Long userId) {
        userValidator.isValidUser(userId);
        User reportedUser = userAdaptor.query(userId);
        List<UserReport> userReports = userReportAdaptor.queryAllByUserId(userId);
        return UserReportInfoVo.of(reportedUser, userReports.size());
    }
}
