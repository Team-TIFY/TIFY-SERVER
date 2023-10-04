package tify.server.api.product.service;


import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.question.adaptor.FavorAnswerAdaptor;

@UseCase
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecommendationUseCase {

    private final ProductAdaptor productAdaptor;

    private final FavorAnswerAdaptor favorAnswerAdaptor;

    public void execute(String categoryName) {
        // 카테고리 이름에 대한 답변이 있는지

        // 전략 패턴 생성

        // 답변들 dto로 변환
    }
}
