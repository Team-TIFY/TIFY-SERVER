package tify.server.api.user.service;

import static org.springframework.http.HttpStatus.*;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.config.security.SecurityUtils;
import tify.server.api.user.model.dto.response.UserReportResponse;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.adaptor.UserReportAdaptor;
import tify.server.domain.domains.user.domain.UserReport;

@UseCase
@RequiredArgsConstructor
public class CreateUserReportUseCase {

    private final UserReportAdaptor userReportAdaptor;
    private final UserAdaptor userAdaptor;

    @Transactional
    public ResponseEntity<UserReportResponse> execute(Long userId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Optional<UserReport> report =
                userReportAdaptor.optionalQueryByFromUserIdAndToUserId(currentUserId, userId);
        if (report.isPresent()) { // 이미 신고가 존재할 때
            UserReportResponse response =
                    UserReportResponse.builder()
                            .fromUserId(currentUserId)
                            .toUserId(userId)
                            .reportSuccess(false)
                            .build();
            return new ResponseEntity<>(response, new HttpHeaders(), BAD_REQUEST);
        } else { // 새로운 신고일 때
            userReportAdaptor.save(
                    UserReport.builder()
                            .fromUserId(currentUserId)
                            .toUserId(userAdaptor.query(userId).getId())
                            .build());
            UserReportResponse response =
                    UserReportResponse.builder()
                            .fromUserId(currentUserId)
                            .toUserId(userId)
                            .reportSuccess(true)
                            .build();
            return new ResponseEntity<>(response, new HttpHeaders(), OK);
        }
    }
}
