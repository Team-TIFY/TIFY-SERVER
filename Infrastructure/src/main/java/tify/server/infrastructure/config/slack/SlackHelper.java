package tify.server.infrastructure.config.slack;


import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.model.block.LayoutBlock;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tify.server.core.helper.SpringEnvironmentHelper;

@Component
@RequiredArgsConstructor
@Slf4j
public class SlackHelper {

    private final SpringEnvironmentHelper springEnvironmentHelper;

    private final MethodsClient methodsClient;

    public void sendNotification(String CHANNEL_ID, List<LayoutBlock> layoutBlocks) {
        //            if (!springEnvironmentHelper.isProdProfile()) {
        //              return;
        //            }
        ChatPostMessageRequest chatPostMessageRequest =
                ChatPostMessageRequest.builder()
                        .channel(CHANNEL_ID)
                        .text("")
                        .blocks(layoutBlocks)
                        .build();
        try {
            methodsClient.chatPostMessage(chatPostMessageRequest);
        } catch (SlackApiException | IOException slackApiException) {
            log.error(slackApiException.toString());
        }
    }
}
