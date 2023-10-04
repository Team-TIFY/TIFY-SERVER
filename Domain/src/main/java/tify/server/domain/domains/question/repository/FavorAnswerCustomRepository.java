package tify.server.domain.domains.question.repository;


import java.util.List;
import java.util.Optional;
import tify.server.domain.domains.question.domain.FavorAnswer;
import tify.server.domain.domains.question.dto.model.FavorAnswerCategoryDto;

public interface FavorAnswerCustomRepository {

    List<FavorAnswerCategoryDto> searchToAnswerCategory(Long currentUserId);

    Optional<FavorAnswer> searchByCategoryAndNumber(Long userId, String category, Long number);
}
