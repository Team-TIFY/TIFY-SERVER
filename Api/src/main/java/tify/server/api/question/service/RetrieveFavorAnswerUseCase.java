package tify.server.api.question.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.api.question.model.vo.FavorAnswerInfoVo;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.FavorAnswerAdaptor;
import tify.server.domain.domains.user.domain.SmallCategory;

@UseCase
@RequiredArgsConstructor
public class RetrieveFavorAnswerUseCase {

    private final FavorAnswerAdaptor favorAnswerAdaptor;

    public List<FavorAnswerInfoVo> retrieveUserFavorAnswers(
            Long userId, List<SmallCategory> smallCategory) {
        return favorAnswerAdaptor.searchBySmallCategory(userId, smallCategory).stream()
                .map(FavorAnswerInfoVo::from)
                .toList();
    }
}
