package tify.server.api.answer.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tify.server.api.answer.model.response.AnswerReportResponse;
import tify.server.api.answer.model.response.NeighborAnswerInfoDTO;
import tify.server.api.answer.model.vo.KnockCountVo;
import tify.server.api.answer.model.vo.KnockInfoVo;
import tify.server.api.answer.model.vo.KnockToMeInfoVo;
import tify.server.api.answer.model.vo.RetrieveAnswerCountVo;
import tify.server.api.answer.model.vo.RetrieveAnswerVo;
import tify.server.api.answer.service.CreateDailyAnswerReportUseCase;
import tify.server.api.answer.service.CreateKnockUseCase;
import tify.server.api.answer.service.RetrieveDailyAnswerCountUseCase;
import tify.server.api.answer.service.RetrieveDailyAnswerUseCase;
import tify.server.api.answer.service.RetrieveKnockUseCase;

@RestController
@RequestMapping(value = "/{questionId}/answers")
@SecurityRequirement(name = "access-token")
@RequiredArgsConstructor
@Tag(name = "4. [답변]")
public class AnswerController {

    private final RetrieveDailyAnswerUseCase retrieveDailyAnswerUseCase;
    private final RetrieveDailyAnswerCountUseCase retrieveDailyAnswerCountUseCase;
    private final CreateDailyAnswerReportUseCase createDailyAnswerReportUseCase;
    private final CreateKnockUseCase createKnockUseCase;
    private final RetrieveKnockUseCase retrieveKnockUseCase;

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
            @PathVariable Long questionId, @PathVariable Long userId) {
        return retrieveDailyAnswerUseCase.executeNeighborAnswerList(questionId, userId);
    }

    @Operation(summary = "친구를 쿡 찌릅니다.")
    @PostMapping("/{userId}/knock")
    public void postKnock(@PathVariable Long questionId, @PathVariable Long userId) {
        createKnockUseCase.execute(questionId, userId);
    }

    @Operation(summary = "특정 친구를 몇 번 찔렀는지를 조회합니다.")
    @GetMapping("/{userId}/knock/count")
    public KnockCountVo getKnockCount(@PathVariable Long questionId, @PathVariable Long userId) {
        return retrieveKnockUseCase.executeCount(questionId, userId);
    }

    @Operation(summary = "나의 모든 쿡 찌르기 내역을 조회합니다.")
    @GetMapping("/knock/count")
    public List<KnockInfoVo> getMyKnockList(@PathVariable Long questionId) {
        return retrieveKnockUseCase.executeAll(questionId);
    }

    @Operation(summary = "나를 쿡 찌른 사람들을 조회합니다.")
    @GetMapping("/knock/to-me")
    public List<KnockToMeInfoVo> getKnockNeighbors(@PathVariable Long questionId) {
        return retrieveKnockUseCase.executeKnockToMe(questionId);
    }
}
