package tify.server.domain.domains.alarm.service;

import static tify.server.domain.domains.alarm.domain.AlarmType.ANNIVERSARY;
import static tify.server.domain.domains.alarm.domain.AlarmType.FAVOR;
import static tify.server.domain.domains.alarm.domain.AlarmType.FRIEND;
import static tify.server.domain.domains.alarm.domain.AlarmType.TODAY;

import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;
import tify.server.core.annotation.DomainService;
import tify.server.domain.common.util.AlarmHistoryUtils;
import tify.server.domain.domains.alarm.dto.AnswerKnockEventDto;
import tify.server.domain.domains.alarm.dto.CreateKnockEventDto;
import tify.server.domain.domains.alarm.dto.ReceiveApplicationEventDto;
import tify.server.domain.domains.alarm.dto.SendApplicationEventDto;
import tify.server.domain.domains.question.adaptor.AnswerAdaptor;
import tify.server.domain.domains.question.adaptor.DailyQuestionAdaptor;
import tify.server.domain.domains.question.adaptor.FavorQuestionAdaptor;
import tify.server.domain.domains.question.adaptor.KnockAdaptor;
import tify.server.domain.domains.question.domain.DailyQuestion;
import tify.server.domain.domains.user.adaptor.NeighborAdaptor;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.User;

@DomainService
@Transactional
@RequiredArgsConstructor
public class AlarmHistoryDomainService {

    private final UserAdaptor userAdaptor;
    private final NeighborAdaptor neighborAdaptor;
    private final DailyQuestionAdaptor dailyQuestionAdaptor;
    private final AnswerAdaptor answerAdaptor;
    private final KnockAdaptor knockAdaptor;
    private final FavorQuestionAdaptor favorQuestionAdaptor;

    private final AlarmHistoryUtils alarmHistoryUtils;

    @Async
    @EventListener
    public void executeToReceiveFriendApplicationAlarm(SendApplicationEventDto dto) {
        User sender = userAdaptor.query(dto.getFromUserId());
        User receiver = userAdaptor.query(dto.getToUserId());
        String title = String.format("%s님의 친구맺기 요청!", sender.getUserId());
        String content = String.format("수락하고 %s님 취향 살펴보기 >", sender.getUserId());
        HashMap<String, Object> newMap = new HashMap<>();
        newMap.put("sendUserId", sender.getUserId());
        newMap.put("receiveUserId", receiver.getUserId());

        if (alarmHistoryUtils.checkUserReceiveAlarm(receiver, title, content, FRIEND)) {
            alarmHistoryUtils.sendMessage(receiver, title, content, newMap, FRIEND);
        }
    }

    @Async
    @EventListener
    public void executeToAcceptFriendApplicationAlarm(ReceiveApplicationEventDto dto) {
        // sender : 요청을 보낸 사람, receiver : 요청을 받은 사람(수락을 누른 사람)
        User sender = userAdaptor.query(dto.getFromUserId());
        User receiver = userAdaptor.query(dto.getToUserId());
        String title = String.format("%s님의 친구맺기 수락!", receiver.getUserId());
        String content = String.format("%s님 취향 살펴보기 >", receiver.getUserId());
        HashMap<String, Object> newMap = new HashMap<>();
        newMap.put("sendUserId", sender.getUserId());
        newMap.put("receiveUserId", receiver.getUserId());

        if (alarmHistoryUtils.checkUserReceiveAlarm(sender, title, content, FRIEND)) {
            alarmHistoryUtils.sendMessage(sender, title, content, newMap, FRIEND);
        }
    }

    @Async
    @EventListener
    public void executeToKnockAlarm(CreateKnockEventDto dto) {
        User sender = userAdaptor.query(dto.getFromUserId());
        User receiver = userAdaptor.query(dto.getToUserId());
        DailyQuestion question = dailyQuestionAdaptor.query(dto.getQuestionId());
        int knockCount =
                knockAdaptor
                        .queryAllByDailyQuestionIdAndUserIdAndKnockedUserId(
                                dto.getQuestionId(), dto.getFromUserId(), dto.getToUserId())
                        .size();
        String title =
                String.format("%s님이 %d번 쿡 찔렀어요 \uD83D\uDC49", sender.getUserId(), knockCount);
        String content =
                String.format(
                        "%s 질문 답변하러 가기 >",
                        question.getLoadingDate()
                                .getDayOfWeek()
                                .getDisplayName(TextStyle.FULL, Locale.KOREA));
        HashMap<String, Object> newMap = new HashMap<>();
        newMap.put("knockUserId", sender.getUserId());
        newMap.put("knockedUserId", receiver.getUserId());

        if (alarmHistoryUtils.checkUserReceiveAlarm(receiver, title, content, TODAY)) {
            alarmHistoryUtils.sendMessage(receiver, title, content, newMap, TODAY);
        }
    }

    @Async
    @EventListener
    public void executeToFriendAnswerAlarm(AnswerKnockEventDto dto) {
        User sender = userAdaptor.query(dto.getFromUserId());
        User receiver = userAdaptor.query(dto.getToUserId());
        String title = String.format("내가 쿡! 찌른 %s님이 답변했어요 \uD83D\uDE4C", receiver.getUserId());
        String content = String.format("%s님의 투데이 답변 확인하러 가기 >", receiver.getUserId());

        HashMap<String, Object> newMap = new HashMap<>();
        newMap.put("knockUserId", sender.getUserId());
        newMap.put("knockedUserId", receiver.getUserId());

        if (alarmHistoryUtils.checkUserReceiveAlarm(sender, title, content, TODAY)) {
            alarmHistoryUtils.sendMessage(sender, title, content, newMap, TODAY);
        }
    }

    @Async
    public void executeToNotAnsweredQuestionAlarm(Long questionId) {
        String title = "지금 바로 답변 가능한 투데이 질문이 있어요 \uD83D\uDC40";
        String content = "내 답변 남기고, 친구들의 답변 구경하기 >";

        DailyQuestion question = dailyQuestionAdaptor.query(questionId);
        List<User> notAnsweredUserList = userAdaptor.queryNotAnsweredUsers(questionId);

        notAnsweredUserList.forEach(
                user -> {
                    if (alarmHistoryUtils.checkUserReceiveAlarm(user, title, content, TODAY)) {
                        HashMap<String, Object> newMap = new HashMap<>();
                        newMap.put("questionId", question.getContent());
                        newMap.put("receiverId", user.getUserId());
                        alarmHistoryUtils.sendMessage(user, title, content, newMap, TODAY);
                    }
                });
    }

    @Async
    public void executeToFriendBirthDayAlarm(List<User> expectedBirthdayUserList) {
        String content = "취향에 딱 맞는 선물을 받을 확률을 올려보세요!\n티피 프로필 공유하러 가기 >";

        expectedBirthdayUserList.forEach(
                user -> {
                    String title =
                            String.format(
                                    "%s님의 생일이 4일 밖에 안남았대요! ", user.getProfile().getUserName());
                    userAdaptor
                            .queryNeighborsByUserId(user.getId())
                            .forEach(
                                    neighbor -> {
                                        if (alarmHistoryUtils.checkUserReceiveAlarm(
                                                neighbor, title, content, ANNIVERSARY)) {
                                            HashMap<String, Object> newMap = new HashMap<>();
                                            newMap.put("neighborUserId", neighbor.getUserId());
                                            alarmHistoryUtils.sendMessage(
                                                    neighbor, title, content, newMap, ANNIVERSARY);
                                        }
                                    });
                });
    }

    @Async
    public void executeToBirthDayAlarm() {
        String content = "취향에 딱 맞는 선물을 받을 확률을 올려보세요!\n티피 프로필 공유하러 가기 >";

        List<User> birthdayUserList = userAdaptor.queryBirthDayUsers();

        birthdayUserList.forEach(
                user -> {
                    String title =
                            String.format(
                                    "%s님, 생일 축하드려요 \uD83C\uDF89", user.getProfile().getUserName());
                    if (alarmHistoryUtils.checkUserReceiveAlarm(
                            user, title, content, ANNIVERSARY)) {
                        HashMap<String, Object> newMap = new HashMap<>();
                        newMap.put("userId", user.getUserId());
                        alarmHistoryUtils.sendMessage(user, title, content, newMap, ANNIVERSARY);
                    }
                });
    }

    @Async
    public void executeToChristmasAlarm() {
        String title = "이번 크리스마스엔 센스있는 산타가 되어볼까요?\uD83C\uDF85";
        String content = "D-5, 티피에서 친구들의 취향 확인하고\n크리스마스 선물 고르기 >";

        List<User> userList = userAdaptor.queryAll();

        userList.forEach(
                user -> {
                    if (alarmHistoryUtils.checkUserReceiveAlarm(
                            user, title, content, ANNIVERSARY)) {
                        HashMap<String, Object> newMap = new HashMap<>();
                        newMap.put("userId", user.getUserId());
                        alarmHistoryUtils.sendMessage(user, title, content, newMap, ANNIVERSARY);
                    }
                });
    }

    @Async
    public void executeToFavorAlarm() {
        String title = "답변 가능한 취향 질문이 남아있어요 \uD83D\uDC40";
        String content = "내 취향 남기러 가기 >";

        int favorQuestionSize = favorQuestionAdaptor.queryAll().size();

        userAdaptor
                .queryNotTotallyFavorAnsweredUsers(favorQuestionSize)
                .forEach(
                        user -> {
                            if (alarmHistoryUtils.checkUserReceiveAlarm(
                                    user, title, content, FAVOR)) {
                                HashMap<String, Object> newMap = new HashMap<>();
                                newMap.put("userId", user.getUserId());
                                alarmHistoryUtils.sendMessage(user, title, content, newMap, FAVOR);
                            }
                        });
    }
}
