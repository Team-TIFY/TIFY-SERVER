package tify.server.api.alarm.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tify.server.api.alarm.model.condition.AlarmCondition;
import tify.server.api.alarm.model.vo.AlarmHistoryVo;
import tify.server.api.alarm.service.RetrieveAlarmHistoryUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "alarm")
@SecurityRequirement(name = "access-token")
@Tag(name = "9. 알림")
public class AlarmHistoryController {

    private final RetrieveAlarmHistoryUseCase retrieveAlarmHistoryUseCase;

    @Operation(summary = "푸시 알림을 조회합니다. (유저, 읽음상태, 제목 필터)")
    @GetMapping
    public List<AlarmHistoryVo> getAlarmsByUser(@ParameterObject AlarmCondition condition) {
        return retrieveAlarmHistoryUseCase.execute(condition);
    }
}
