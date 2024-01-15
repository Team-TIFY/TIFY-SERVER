package tify.server.domain.common.util;


import io.github.jav.exposerversdk.ExpoPushMessage;
import io.github.jav.exposerversdk.ExpoPushMessageTicketPair;
import io.github.jav.exposerversdk.ExpoPushTicket;
import io.github.jav.exposerversdk.PushClient;
import io.github.jav.exposerversdk.PushClientException;
import io.github.jav.exposerversdk.PushNotificationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.stereotype.Component;
import tify.server.domain.domains.alarm.adaptor.AlarmHistoryAdaptor;
import tify.server.domain.domains.alarm.domain.AlarmHistory;
import tify.server.domain.domains.alarm.domain.AlarmType;
import tify.server.domain.domains.alarm.exception.ExpoPushTicketException;
import tify.server.domain.domains.alarm.exception.NotValidExpoTokenException;
import tify.server.domain.domains.user.domain.User;

@Slf4j
@Component
@RequiredArgsConstructor
public class AlarmHistoryUtils {

    private final AlarmHistoryAdaptor alarmHistoryAdaptor;

    public void sendMessage(
            User user, String title, String body, Map<String, Object> data, AlarmType alarmType) {

        String token = user.getExpoToken();
        if (token == null) {
            log.info("token = {}", user.getExpoToken());
            throw NotValidExpoTokenException.EXCEPTION;
        }
        if (!PushClient.isExponentPushToken(token)) {
            log.info("token = {}", user.getExpoToken());
            throw NotValidExpoTokenException.EXCEPTION;
        }
        ExpoPushMessage expoPushMessage = new ExpoPushMessage();
        expoPushMessage.getTo().add(token);
        expoPushMessage.setTitle(title);
        expoPushMessage.setBody(body);
        expoPushMessage.setData(data);

        List<ExpoPushMessage> expoPushMessages = new ArrayList<>();
        expoPushMessages.add(expoPushMessage);

        try {
            PushClient pushClient = new PushClient();
            List<List<ExpoPushMessage>> chunks =
                    pushClient.chunkPushNotifications(expoPushMessages);

            List<CompletableFuture<List<ExpoPushTicket>>> messageRepliesFutures = new ArrayList<>();

            for (List<ExpoPushMessage> chunk : chunks) {
                messageRepliesFutures.add(pushClient.sendPushNotificationsAsync(chunk));
            }

            AlarmHistory alarm =
                    AlarmHistory.builder()
                            .title(title)
                            .content(body)
                            .userId(user.getId())
                            .alarmType(alarmType)
                            .build();
            alarmHistoryAdaptor.save(alarm);
            List<ExpoPushTicket> allTickets = new ArrayList<>();
            for (CompletableFuture<List<ExpoPushTicket>> messageReplyFuture :
                    messageRepliesFutures) {
                try {
                    allTickets.addAll(messageReplyFuture.get());
                } catch (InterruptedException | ExecutionException e) {
                    log.error("error message = {}", e.getMessage());
                    throw ExpoPushTicketException.EXCEPTION;
                }
            }

            List<ExpoPushMessageTicketPair<ExpoPushMessage>> zippedMessagesTickets =
                    pushClient.zipMessagesTickets(expoPushMessages, allTickets);

            List<ExpoPushMessageTicketPair<ExpoPushMessage>> okTicketMessages =
                    pushClient.filterAllSuccessfulMessages(zippedMessagesTickets);
            String okTicketMessagesString =
                    okTicketMessages.stream()
                            .map(p -> "Title: " + p.message.getTitle() + ", Id:" + p.ticket.getId())
                            .collect(Collectors.joining(","));
            log.info(
                    "Recieved OK ticket for "
                            + okTicketMessages.size()
                            + " messages: "
                            + okTicketMessagesString);

            List<ExpoPushMessageTicketPair<ExpoPushMessage>> errorTicketMessages =
                    pushClient.filterAllMessagesWithError(zippedMessagesTickets);
            String errorTicketMessagesString =
                    errorTicketMessages.stream()
                            .map(
                                    p ->
                                            "id: "
                                                    + user.getId()
                                                    + ", "
                                                    + "Title: "
                                                    + p.message.getTitle()
                                                    + ", Error: "
                                                    + p.ticket.getDetails().getError())
                            .collect(Collectors.joining(","));
            log.info(
                    "Recieved ERROR ticket for "
                            + errorTicketMessages.size()
                            + " messages: "
                            + errorTicketMessagesString);
        } catch (PushClientException | PushNotificationException | GenericJDBCException e) {
            log.info("error message = {}", e.getMessage());
            throw ExpoPushTicketException.EXCEPTION;
        }
    }

    public Boolean checkUserReceiveAlarm(
            User user, String title, String content, AlarmType alarmType) {
        if ((!user.getReceiveAlarm()
                || user.getExpoToken() == null
                || !user.getExpoToken().startsWith("ExponentPushToken"))) {
            AlarmHistory alarm =
                    AlarmHistory.builder()
                            .userId(user.getId())
                            .title(title)
                            .content(content)
                            .alarmType(alarmType)
                            .build();
            alarmHistoryAdaptor.save(alarm);
            return false;
        }
        return true;
    }
}
