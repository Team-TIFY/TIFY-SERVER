package tify.server.api.question.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tify.server.api.question.model.request.PostAnswerRequest;
import tify.server.api.question.model.response.GetDailyQuestionResponse;
import tify.server.api.question.model.vo.DailyQuestionInfoVo;
import tify.server.api.question.service.CreateAnswerUseCase;
import tify.server.api.question.service.RetrieveDailyQuestionUseCase;
import tify.server.core.converter.DateTimeConverter;

@RestController
@Slf4j
@RequestMapping(value = "/daily-questions")
@SecurityRequirement(name = "access-token")
@RequiredArgsConstructor
@Tag(name = "3. [데일리 질문]")
public class QuestionController {

    private final RetrieveDailyQuestionUseCase retrieveDailyQuestionUseCase;
    private final CreateAnswerUseCase createAnswerUseCase;

    @Operation(summary = "날짜에 맞는 질문을 조회합니다.")
    @GetMapping
    public GetDailyQuestionResponse getDailyQuestion(
            @Schema(example = "2023-07-26") @RequestParam String loadingDate) {
        DailyQuestionInfoVo dailyQuestionInfoVo =
                retrieveDailyQuestionUseCase.execute(DateTimeConverter.getDate(loadingDate));
        return GetDailyQuestionResponse.from(dailyQuestionInfoVo);
    }

    @Operation(summary = "데일리 질문에 답변을 작성합니다.")
    @PostMapping("/{questionId}/answers")
    public void postAnswer(
            @PathVariable Long questionId, @RequestBody @Valid PostAnswerRequest body) {
        createAnswerUseCase.execute(questionId, body);
    }
}
