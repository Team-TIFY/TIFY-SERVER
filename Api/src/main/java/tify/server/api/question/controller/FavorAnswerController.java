package tify.server.api.question.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tify.server.api.question.model.request.PostFavorAnswerRequest;
import tify.server.api.question.service.CreateFavorAnswerUseCase;
import tify.server.api.question.service.RetrieveFavorQuestionUseCase;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping(value = "/favor-questions")
@SecurityRequirement(name = "access-token")
@RequiredArgsConstructor
@Tag(name = "4. [취향 질문]")
public class FavorAnswerController {
  
  private final CreateFavorAnswerUseCase createFavorAnswerUseCase;
  
  @Operation(summary = "취향 질문에 대한 답변을 작성합니다.")
  @PostMapping("/{favorQuestionId}/answers")
  public void postFavorAnswer(
          @PathVariable Long favorQuestionId, @RequestBody @Valid PostFavorAnswerRequest body) {
    createFavorAnswerUseCase.execute(favorQuestionId, body);
  }
}
