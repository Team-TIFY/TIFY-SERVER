package tify.server.domain.domains.user.adaptor;


import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.user.domain.UserReport;
import tify.server.domain.domains.user.exception.UserReportNotFoundException;
import tify.server.domain.domains.user.repository.UserReportRepository;

@Adaptor
@RequiredArgsConstructor
public class UserReportAdaptor {

    private final UserReportRepository userReportRepository;

    public UserReport query(Long id) {
        return userReportRepository
                .findById(id)
                .orElseThrow(() -> UserReportNotFoundException.EXCEPTION);
    }

    public void save(UserReport userReport) {
        userReportRepository.save(userReport);
    }
}
