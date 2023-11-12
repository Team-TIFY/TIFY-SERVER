package tify.server.domain.domains.question.adaptor;


import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.Adaptor;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.dto.model.FavorAnswerCategoryDto;
import tify.server.domain.domains.question.dto.model.FavorAnswerVo;
import tify.server.domain.domains.question.exception.FavorAnswerNotFoundException;
import tify.server.domain.domains.question.repository.FavorAnswerRepository;
import tify.server.domain.domains.user.domain.DetailCategory;
import tify.server.domain.domains.user.domain.SmallCategory;

@Adaptor
@RequiredArgsConstructor
public class FavorAnswerAdaptor {

    private final FavorAnswerRepository favorAnswerRepository;

    public FavorAnswer query(Long favorAnswerId) {
        return favorAnswerRepository
                .findById(favorAnswerId)
                .orElseThrow(() -> FavorAnswerNotFoundException.EXCEPTION);
    }

    public List<FavorAnswerCategoryDto> searchCategories(Long currentUserId) {
        return favorAnswerRepository.searchToAnswerCategory(currentUserId);
    }

    public boolean existsByDetailCategoryAndUserId(DetailCategory detailCategory, Long userId) {
        return favorAnswerRepository
                .existsByFavorQuestion_FavorQuestionCategory_DetailCategoryAndUserId(
                        detailCategory, userId);
    }

    public FavorAnswer searchByCategoryNameAndNumber(
            Long userId, String categoryName, Long number) {
        return favorAnswerRepository
                .searchByCategoryAndNumber(userId, categoryName, number)
                .orElseThrow(() -> FavorAnswerNotFoundException.EXCEPTION);
    }

    public List<FavorAnswerVo> searchBySmallCategory(
            Long userId, List<SmallCategory> smallCategory) {
        return favorAnswerRepository.getFavorAnswerBySmallCategory(userId, smallCategory);
    }
}
