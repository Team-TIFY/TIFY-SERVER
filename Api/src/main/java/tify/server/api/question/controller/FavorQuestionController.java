package tify.server.api.question.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tify.server.api.config.security.SecurityUtils;
import tify.server.api.question.model.request.PostFavorAnswerRequest;
import tify.server.api.question.model.response.RetrieveCategoryIsAnsweredDTO;
import tify.server.api.question.model.response.RetrieveIsAnsweredByDetailCategoryResponse;
import tify.server.api.question.model.vo.FavorAnswerInfoVo;
import tify.server.api.question.model.vo.FavorQuestionInfoVo;
import tify.server.api.question.service.CreateFavorAnswerUseCase;
import tify.server.api.question.service.RetrieveFavorAnswerUseCase;
import tify.server.api.question.service.RetrieveFavorQuestionUseCase;
import tify.server.api.question.service.RetrieveIsAnsweredUseCase;
import tify.server.domain.domains.user.domain.SmallCategory;

@RestController
@Slf4j
@RequestMapping(value = "/favor-questions")
@SecurityRequirement(name = "access-token")
@RequiredArgsConstructor
@Tag(name = "5. [취향 질문]")
public class FavorQuestionController {

    private final RetrieveFavorQuestionUseCase retrieveFavorQuestionUseCase;
    private final RetrieveIsAnsweredUseCase retrieveisAnsweredUseCase;
    private final CreateFavorAnswerUseCase createFavorAnswerUseCase;
    private final RetrieveFavorAnswerUseCase retrieveFavorAnswerUseCase;

    @Operation(summary = "취향 질문 정보 조회")
    @GetMapping
    public FavorQuestionInfoVo getFavorQuestion(
            @RequestParam String category, @RequestParam Long number) {
        return retrieveFavorQuestionUseCase.retrieveQuestion(category, number);
    }

    @Operation(summary = "카테고리별 유저의 답변 여부를 조회")
    @GetMapping("/isAnswered")
    public List<RetrieveCategoryIsAnsweredDTO> favorCategoryIsAnswered() {
        return retrieveisAnsweredUseCase.retrieveIsAnsweredBySmallCategory();
    }

    @Operation(summary = "취향 질문에 대한 답변을 작성합니다.")
    @PostMapping("/answers")
    public void postFavorAnswer(@RequestBody @Valid PostFavorAnswerRequest body) {
        createFavorAnswerUseCase.execute(body);
    }

    @Operation(summary = "DetailCategory(FE기준 소분류) 별 유저 답변 여부를 조회")
    @GetMapping("/isAnswered/detail-category")
    public List<RetrieveIsAnsweredByDetailCategoryResponse> getExistsFavorAnswerByDetailCategory(
            @Schema(description = "중분류", implementation = SmallCategory.class) @RequestParam
                    SmallCategory smallCategory) {
        return retrieveisAnsweredUseCase.retrieveIsAnsweredByDetailCategory(smallCategory);
    }

    @Operation(summary = "SmallCategory(FE기준 중분류) 별 유저 취향 조회")
    @GetMapping("/answers")
    public List<FavorAnswerInfoVo> getFavorAnswerBySmallCategory(
            @Schema(description = "중분류", implementation = SmallCategory.class) @RequestParam
                    SmallCategory smallCategory) {
        return retrieveFavorAnswerUseCase.retrieveUserFavorAnswers(
                SecurityUtils.getCurrentUserId(), smallCategory);
    }
}
