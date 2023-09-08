package tify.server.domain.domains.question.repository;


import java.util.List;
import tify.server.domain.domains.question.dto.model.FavorAnswerCategoryDto;

public interface FavorAnswerCustomRepository {

    List<FavorAnswerCategoryDto> searchToAnswerCategory(Long currentUserId);
}
