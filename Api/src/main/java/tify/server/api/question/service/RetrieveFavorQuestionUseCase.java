package tify.server.api.question.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.question.model.vo.FavorQuestionInfoVo;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.question.adaptor.FavorQuestionAdaptor;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RetrieveFavorQuestionUseCase {

    private final FavorQuestionAdaptor favorQuestionAdaptor;

    public FavorQuestionInfoVo retrieveQuestion(String category, Long number) {
        return FavorQuestionInfoVo.from(
                favorQuestionAdaptor.queryFavorQuestionByCategoryNameAndNumber(category, number));
    }
}
