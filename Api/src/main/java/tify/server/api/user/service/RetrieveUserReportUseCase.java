package tify.server.api.user.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.api.user.model.dto.vo.UserReportInfoVo;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.adaptor.UserReportAdaptor;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.domain.UserReport;

@UseCase
@RequiredArgsConstructor
public class RetrieveUserReportUseCase {

    private final UserAdaptor userAdaptor;
    private final UserReportAdaptor userReportAdaptor;

    public UserReportInfoVo execute(Long userId) {
        User reportedUser = userAdaptor.query(userId);
        List<UserReport> userReports = userReportAdaptor.queryAllByUserId(userId);
        return UserReportInfoVo.of(reportedUser, userReports.size());
    }
}
