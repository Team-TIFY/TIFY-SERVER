package tify.server.domain.scheduler.alarm;


import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tify.server.domain.domains.alarm.service.AlarmHistoryDomainService;
import tify.server.domain.domains.question.adaptor.DailyQuestionAdaptor;
import tify.server.domain.domains.question.domain.DailyQuestion;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.User;

@Component
@RequiredArgsConstructor
public class AlarmScheduler {

    private final UserAdaptor userAdaptor;
    private final DailyQuestionAdaptor dailyQuestionAdaptor;
    private final AlarmHistoryDomainService alarmHistoryDomainService;

    @Transactional
    @Scheduled(cron = "0 0 17 * * SUN", zone = "Asia/Seoul")
    public void executeToNotAnsweredQuestionAlarm() {
        LocalDate today = LocalDate.now();
        DailyQuestion dailyQuestion = dailyQuestionAdaptor.queryByLoadingDate(today);
        alarmHistoryDomainService.executeToNotAnsweredQuestionAlarm(dailyQuestion.getId());
    }

    @Transactional
    @Scheduled(cron = "0 0 21 * * *", zone = "Asia/Seoul")
    public void executeToFriendBirthDayAlarm() {
        LocalDate fourDaysLater = LocalDate.now().plusDays(4);
        String monthAndYear =
                String.format(
                        "%02d%02d",
                        fourDaysLater.getMonth().getValue(), fourDaysLater.getDayOfMonth());
        List<User> expectedBirthdayUserList = userAdaptor.queryExpectedBirthdayUsers(monthAndYear);
        alarmHistoryDomainService.executeToFriendBirthDayAlarm(expectedBirthdayUserList);
    }

    @Transactional
    @Scheduled(cron = "0 0 10 * * *", zone = "Asia/Seoul")
    public void executeToBirthDayAlarm() {
        alarmHistoryDomainService.executeToBirthDayAlarm();
    }

    @Transactional
    @Scheduled(cron = "0 0 18 7 * *", zone = "Asia/Seoul")
    public void executeToFavorAlarm() {
        alarmHistoryDomainService.executeToFavorAlarm();
    }
}
