package tify.server.domain.domains.question.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.core.annotation.DomainService;
import tify.server.domain.domains.question.adaptor.FavorQuestionAdaptor;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.domain.FavorQuestion;
import tify.server.domain.domains.question.dto.model.FavorAnswerDto;
import tify.server.domain.domains.question.validator.FavorQuestionValidator;
import tify.server.domain.domains.user.adaptor.UserAdaptor;
import tify.server.domain.domains.user.domain.User;

@DomainService
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavorQuestionDomainService {

    private final FavorQuestionAdaptor favorQuestionAdaptor;
    private final FavorQuestionValidator favorQuestionValidator;
    private final UserAdaptor userAdaptor;

    @Transactional
    public void createFavorAnswer(Long userId, String categoryName, List<FavorAnswerDto> answers) {

        // categoryName에 대한 질문이 존재하는가?
        favorQuestionValidator.isExistFavorQuestionCategory(categoryName);

        List<FavorQuestion> favorQuestions =
                favorQuestionAdaptor.queryFavorQuestionByCategoryName(categoryName);

        // 답변 가능 여부 검증
        favorQuestionValidator.isValidateFavorAnswerToQuestion(favorQuestions, userId);

        List<FavorAnswer> favorAnswers =
                answers.stream()
                        .map(
                                answer ->
                                        FavorAnswer.builder()
                                                .answerContent(answer.getAnswer())
                                                .userId(userId)
                                                .favorQuestion(
                                                        favorQuestions.get(answer.getNum() - 1))
                                                .build())
                        .toList();
        // 답변 작성
        favorQuestionAdaptor.favorAnswerSaveAll(favorAnswers);
        User user = userAdaptor.query(userId);
        user.updateFavor();
    }
}
