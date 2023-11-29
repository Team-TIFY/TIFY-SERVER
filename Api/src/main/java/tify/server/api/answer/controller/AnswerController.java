package tify.server.api.answer.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tify.server.api.answer.model.response.AnswerReportResponse;
import tify.server.api.answer.model.response.NeighborAnswerInfoDTO;
import tify.server.api.answer.model.vo.RetrieveAnswerCountVo;
import tify.server.api.answer.model.vo.RetrieveAnswerVo;
import tify.server.api.answer.service.CreateDailyAnswerReportUseCase;
import tify.server.api.answer.service.RetrieveDailyAnswerCountUseCase;
import tify.server.api.answer.service.RetrieveDailyAnswerUseCase;

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
    public List<RetrieveAnswerVo> getAnswers(@PathVariable Long questionId) {
        return retrieveDailyAnswerUseCase.execute(questionId);
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

    @Operation(
            summary = "특정 유저의 친구들이 해당 데일리 질문에 답변을 남겼는지 조회하고, 남겼다면 답변의 정보를 조회합니다.",
            description = "답변을 남기지 않았다면 답변 필드가 null")
    @GetMapping("/{userId}/neighbors")
    public List<NeighborAnswerInfoDTO> getNeighborAnswerInfoList(
            @PathVariable Long questionId,
            @PathVariable Long userId,
            @ParameterObject @PageableDefault(size = 10) Pageable pageable) {
        return retrieveDailyAnswerUseCase.executeNeighborAnswerList(questionId, userId, pageable);
    }
}
