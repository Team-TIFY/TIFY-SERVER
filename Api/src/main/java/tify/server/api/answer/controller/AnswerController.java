package tify.server.api.answer.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import tify.server.api.answer.model.response.AnswerReportResponse;
import tify.server.api.answer.model.vo.RetrieveAnswerCountVo;
import tify.server.api.answer.model.vo.RetrieveAnswerVo;
import tify.server.api.answer.service.CreateDailyAnswerReportUseCase;
import tify.server.api.answer.service.RetrieveDailyAnswerCountUseCase;
import tify.server.api.answer.service.RetrieveDailyAnswerUseCase;
import tify.server.api.common.slice.SliceResponse;

@RestController
@RequestMapping(value = "/{questionId}/answers")
@SecurityRequirement(name = "access-token")
@RequiredArgsConstructor
@Tag(name = "4. [답변]")
public class AnswerController {

    private final RetrieveDailyAnswerUseCase retrieveDailyAnswerUseCase;
    private final RetrieveDailyAnswerCountUseCase retrieveDailyAnswerCountUseCase;
    private final CreateDailyAnswerReportUseCase createDailyAnswerReportUseCase;

    @Operation(summary = "데일리 질문에 대한 답변을 모두 조회합니다.")
    @GetMapping
    public SliceResponse<RetrieveAnswerVo> getAnswers(
            @PathVariable Long questionId,
            @ParameterObject @PageableDefault(size = 10) Pageable pageable) {
        return retrieveDailyAnswerUseCase.execute(questionId, pageable);
    }

    @Operation(summary = "데일리 질문에 대한 답변 개수를 조회합니다.")
    @GetMapping(value = "/counts")
    public RetrieveAnswerCountVo getAnswerCounts(@PathVariable Long questionId) {
        return retrieveDailyAnswerCountUseCase.execute(questionId);
    }

    @Operation(summary = "데일리 질문에 대한 답변을 신고합니다.")
    @PostMapping(value = "/{answerId}/report")
    public AnswerReportResponse postAnswerReport(
            @PathVariable Long questionId, @PathVariable Long answerId) {
        return createDailyAnswerReportUseCase.execute(answerId);
    }
}
