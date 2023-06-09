package tify.server.api.example;


import io.swagger.v3.oas.annotations.Operation;
import java.util.Objects;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tify.server.api.example.dto.HealthCheckResponse;

@RestController
@RequestMapping(value = "/health")
public class HealthCheckController {

    @Operation(summary = "테스트용 컨트롤러입니다.")
    @GetMapping
    public HealthCheckResponse healthCheck(@RequestParam(required = false) String check) {
        if (Objects.equals(check, "error")) {
            throw new RuntimeException();
        }
        return HealthCheckResponse.from("health");
    }
}
