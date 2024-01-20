package tify.server.domain.domains.question.adaptor;


import java.util.List;
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
import tify.server.domain.domains.user.domain.SmallCategory;

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

    public FavorAnswer save(FavorAnswer favorAnswer) {
        return favorAnswerRepository.save(favorAnswer);
    }

    public void favorAnswerSaveAll(List<FavorAnswer> favorAnswers) {
        favorAnswerRepository.saveAll(favorAnswers);
    }

    public boolean existQueryByFavorQuestionCategoryAndUser(
            FavorQuestionCategory favorQuestionCategory, Long userId) {
        return favorAnswerRepository.existsByFavorQuestion_FavorQuestionCategoryAndUserId(
                favorQuestionCategory, userId);
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

    public List<FavorQuestion> queryFavorQuestionByCategoryName(String categoryName) {
        return favorQuestionRepository.findAllByFavorQuestionCategory_NameOrderByNumber(
                categoryName);
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

    public List<FavorQuestionCategory> queryAllFavorQuestionCategory() {
        return favorQuestionCategoryRepository.findAll();
    }

    public List<FavorQuestionCategory> queryBySmallCategory(SmallCategory smallCategory) {
        return favorQuestionCategoryRepository.findBySmallCategory(smallCategory);
    }

    public List<FavorQuestion> queryAll() {
        return favorQuestionRepository.findAll();
    }
}
