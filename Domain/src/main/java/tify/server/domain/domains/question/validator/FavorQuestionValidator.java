package tify.server.domain.domains.question.validator;

import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Validator;
import tify.server.domain.domains.question.adaptor.FavorQuestionAdaptor;
import tify.server.domain.domains.question.domain.FavorQuestion;
import tify.server.domain.domains.question.exception.AlreadyAnsweredFavorQuestionException;

@Validator
@RequiredArgsConstructor
public class FavorQuestionValidator {
  
  private final FavorQuestionAdaptor favorQuestionAdaptor;
  
  public void isValidateFavorAnswerToQuestion(FavorQuestion favorQuestion, Long userId) {
    // 유저가 이미 해당 질문에 대한 답을 남겼는지
    if (favorQuestionAdaptor.existQueryByFavorQuestionAndUser(favorQuestion.getFavorQuestionCategory(), userId)) {
      throw AlreadyAnsweredFavorQuestionException.EXCEPTION;
    }
  }
}
