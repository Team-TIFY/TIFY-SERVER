package tify.server.api.alarm.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tify.server.api.alarm.model.vo.AlarmHistoryVo;
import tify.server.api.alarm.service.CreateAlarmHistoryUseCase;
import tify.server.api.alarm.service.RetrieveAlarmHistoryUseCase;
import tify.server.core.consts.Status;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "alarm")
@SecurityRequirement(name = "access-token")
@Tag(name = "9. 알림")
public class AlarmHistoryController {

    private final CreateAlarmHistoryUseCase createAlarmHistoryUseCase;
    private final RetrieveAlarmHistoryUseCase retrieveAlarmHistoryUseCase;

    @Operation(summary = "푸시 알림을 조회합니다. (유저 필터)")
    @GetMapping
    public List<AlarmHistoryVo> getAlarmsByUser(@RequestParam Long userId) {
        return retrieveAlarmHistoryUseCase.executeToUser(userId);
    }

    @Operation(summary = "푸시 알림을 조회하니다. (읽음/읽지않음 상태 필터)")
    @GetMapping
    public List<AlarmHistoryVo> getAlarmsByUser(
            @RequestParam @Parameter(description = "읽음(Y)/읽지않음(N)") Status isRead) {
        return retrieveAlarmHistoryUseCase.executeToIsRead(isRead);
    }

    @Operation(summary = "푸시 알림을 조회합니다. (제목 필터)")
    @GetMapping
    public List<AlarmHistoryVo> getAlarmsByTitle(@RequestParam String title) {
        return retrieveAlarmHistoryUseCase.executeToTitle(title);
    }
}
