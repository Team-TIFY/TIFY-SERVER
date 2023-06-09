package tify.server.infrastructure.config.slack;


import com.slack.api.model.block.LayoutBlock;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SlackErrorNotificationProvider {

    private final SlackHelper slackHelper;

    private final int MAX_LEN = 500;

    @Value("${SLACK_CHANNEL_ID}")
    private String CHANNEL_ID;

    public String getErrorStack(Throwable throwable) {
        String exceptionAsString = Arrays.toString(throwable.getStackTrace());
        int cutLength = Math.min(exceptionAsString.length(), MAX_LEN);
        return exceptionAsString.substring(0, cutLength);
    }

    @Async
    public void sendNotification(List<LayoutBlock> layoutBlocks) {
        System.out.println(CHANNEL_ID);

        slackHelper.sendNotification(CHANNEL_ID, layoutBlocks);
    }
}
