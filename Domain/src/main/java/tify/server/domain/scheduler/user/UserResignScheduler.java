package tify.server.domain.scheduler.user;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tify.server.domain.domains.question.repository.AnswerReportRepository;
import tify.server.domain.domains.question.repository.AnswerRepository;
import tify.server.domain.domains.question.repository.FavorAnswerRepository;
import tify.server.domain.domains.question.repository.KnockRepository;
import tify.server.domain.domains.user.domain.User;
import tify.server.domain.domains.user.repository.NeighborRepository;
import tify.server.domain.domains.user.repository.UserBlockRepository;
import tify.server.domain.domains.user.repository.UserReportRepository;
import tify.server.domain.domains.user.repository.UserRepository;
import tify.server.domain.domains.user.service.UserDomainService;

@Component
@RequiredArgsConstructor
public class UserResignScheduler {

    private final UserDomainService userDomainService;

    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final AnswerReportRepository answerReportRepository;
    private final FavorAnswerRepository favorAnswerRepository;
    private final KnockRepository knockRepository;
    private final NeighborRepository neighborRepository;
    private final UserBlockRepository userBlockRepository;
    private final UserReportRepository userReportRepository;

    @Transactional
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
    public void deleteResignedUsers() {
        List<User> resignedUserList = userDomainService.getResignedUsers();
        resignedUserList.forEach(user -> {
            answerRepository.deleteAllByUserId(user.getId());
            answerReportRepository.deleteAllByReportUserId(user.getId());
            favorAnswerRepository.deleteAllByUserId(user.getId());
            knockRepository.deleteAllByUserIdOrKnockedUserId(user.getId(), user.getId());
            neighborRepository.deleteAllByFromUserIdOrToUserId(user.getId(), user.getId());
            userBlockRepository.deleteAllByFromUserIdOrToUserId(user.getId(), user.getId());
            userReportRepository.deleteAllByFromUserIdOrToUserId(user.getId(), user.getId());
            userRepository.delete(user);
        });
    }
}
