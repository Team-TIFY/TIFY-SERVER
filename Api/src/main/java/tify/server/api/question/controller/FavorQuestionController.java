package tify.server.api.question.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tify.server.api.question.model.response.RetrieveCategoryIsAnsweredDTO;
import tify.server.api.question.model.vo.FavorQuestionInfoVo;
import tify.server.api.question.service.RetrieveFavorQuestionUseCase;
import tify.server.api.question.service.RetrieveIsAnsweredUseCase;

@RestController
@Slf4j
@RequestMapping(value = "/favor-questions")
@SecurityRequirement(name = "access-token")
@RequiredArgsConstructor
@Tag(name = "4. [취향 질문]")
public class FavorQuestionController {

    private final RetrieveFavorQuestionUseCase retrieveFavorQuestionUseCase;
    private final RetrieveIsAnsweredUseCase retrieveisAnsweredUseCase;

    @Operation(summary = "취향 질문 정보 조회")
    @GetMapping
    public FavorQuestionInfoVo getFavorQuestion(
            @RequestParam String category, @RequestParam Long number) {
        return retrieveFavorQuestionUseCase.retrieveQuestion(category, number);
    }

    @Operation(summary = "카테고리별 유저의 답변 여부를 조회")
    @GetMapping("/isAnswered")
    public List<RetrieveCategoryIsAnsweredDTO> favorCategoryIsAnswered() {
        return retrieveisAnsweredUseCase.retrieveIsAnswered();
    }
}
