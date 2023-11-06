package tify.server.api.product.service;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.common.slice.SliceResponse;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.dto.ProductCategoryCondition;
import tify.server.domain.domains.product.dto.ProductRetrieveDTO;
import tify.server.domain.domains.question.adaptor.FavorQuestionAdaptor;
import tify.server.domain.domains.user.domain.SmallCategory;

@UseCase
@RequiredArgsConstructor
public class RetrieveProductListUseCase {

    private final ProductAdaptor productAdaptor;
    private final FavorQuestionAdaptor favorQuestionAdaptor;

    @Transactional(readOnly = true)
    public SliceResponse<ProductRetrieveDTO> executeToSmallCategory(
            List<SmallCategory> smallCategory, Pageable pageable) {
        List<Long> categoryIdList =
                smallCategory.stream()
                        .map(
                                category ->
                                        favorQuestionAdaptor.queryBySmallCategory(category).getId())
                        .toList();
        return SliceResponse.of(
                productAdaptor.searchBySmallCategoryId(
                        new ProductCategoryCondition(categoryIdList, pageable)));
    }
}
