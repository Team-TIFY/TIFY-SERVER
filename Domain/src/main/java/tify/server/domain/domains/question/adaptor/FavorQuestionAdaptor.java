package tify.server.domain.domains.question.adaptor;


import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.domain.FavorQuestion;
import tify.server.domain.domains.question.domain.FavorQuestionCategory;
import tify.server.domain.domains.question.exception.FavorAnswerNotFoundException;
import tify.server.domain.domains.question.exception.FavorQuestionCategoryNotFoundException;
import tify.server.domain.domains.question.exception.FavorQuestionNotFoundException;
import tify.server.domain.domains.question.repository.FavorAnswerRepository;
import tify.server.domain.domains.question.repository.FavorQuestionCategoryRepository;
import tify.server.domain.domains.question.repository.FavorQuestionRepository;

import java.util.List;

@Adaptor
@RequiredArgsConstructor
public class FavorQuestionAdaptor {

    private final FavorQuestionRepository favorQuestionRepository;
    private final FavorAnswerRepository favorAnswerRepository;
    private final FavorQuestionCategoryRepository favorQuestionCategoryRepository;

    public FavorAnswer queryFavorAnswer(Long favorAnswerId) {
        return favorAnswerRepository
                .findById(favorAnswerId)
                .orElseThrow(() -> FavorAnswerNotFoundException.EXCEPTION);
    }
    
    public FavorAnswer save(FavorAnswer favorAnswer) { return favorAnswerRepository.save(favorAnswer);}
    
    public void favorAnswerSaveAll(List<FavorAnswer> favorAnswers) {
        favorAnswerRepository.saveAll(favorAnswers);
    }
    
    public boolean existQueryByFavorQuestionAndUser(FavorQuestionCategory favorQuestionCategory, Long userId) {
        return favorAnswerRepository.existByFavorQuestionCategoryAndUserId(favorQuestionCategory, userId);
    }
    
    public FavorQuestionCategory queryFavorQuestionCategory(Long favorQuestionCategoryId) {
        return favorQuestionCategoryRepository
                .findById(favorQuestionCategoryId)
                .orElseThrow(() -> FavorQuestionCategoryNotFoundException.EXCEPTION);
    }

    public FavorQuestion queryFavorQuestion(Long favorQuestionId) {
        return favorQuestionRepository
                .findById(favorQuestionId)
                .orElseThrow(() -> FavorQuestionNotFoundException.EXCEPTION);
    }

    public FavorQuestion queryFavorQuestionByCategoryNameAndNumber(
            String favorQuestionCategoryName, Long number) {
        return favorQuestionRepository
                .findByFavorQuestionCategory_NameAndNumber(
                        queryFavorQuestionCategoryByName(favorQuestionCategoryName).getName(),
                        number)
                .orElseThrow(() -> FavorQuestionNotFoundException.EXCEPTION);
    }

    public FavorQuestionCategory queryFavorQuestionCategoryByName(
            String favorQuestionCategoryName) {
        return favorQuestionCategoryRepository
                .findByName(favorQuestionCategoryName)
                .orElseThrow(() -> FavorQuestionCategoryNotFoundException.EXCEPTION);
    }
}
