package tify.server.api.alarm.controller;


import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tify.server.api.alarm.service.RetrieveAlarmHistoryUseCase;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "alarm")
@SecurityRequirement(name = "access-token")
@Tag(name = "9. 알림")
public class AlarmHistoryController {

    private final RetrieveAlarmHistoryUseCase retrieveAlarmHistoryUseCase;
}
