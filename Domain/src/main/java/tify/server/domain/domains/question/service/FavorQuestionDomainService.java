package tify.server.domain.domains.question.service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.core.annotation.DomainService;
import tify.server.domain.domains.question.adaptor.FavorQuestionAdaptor;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.domain.FavorQuestion;
import tify.server.domain.domains.question.dto.model.FavorAnswerDto;
import tify.server.domain.domains.question.validator.FavorQuestionValidator;

import java.util.List;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavorQuestionDomainService {
  
  private final FavorQuestionAdaptor favorQuestionAdaptor;
  private final FavorQuestionValidator favorQuestionValidator;
  
  @Transactional
  public void createFavorAnswer(Long favorQuestionId, Long userId, List<FavorAnswerDto> answers) {
    FavorQuestion favorQuestion = favorQuestionAdaptor.queryFavorQuestion(favorQuestionId);
    // 답변 가능 여부 검증
    favorQuestionValidator.isValidateFavorAnswerToQuestion(favorQuestion, userId);
    
    List<FavorAnswer> favorAnswers = answers.stream().map(answer -> FavorAnswer.builder().answerContent(answer.getAnswer()).userId(userId).favorQuestion(favorQuestion).num(answer.getNum()).build()).toList();
    // 답변 작성
    favorQuestionAdaptor.favorAnswerSaveAll(favorAnswers);
    
  }
}
