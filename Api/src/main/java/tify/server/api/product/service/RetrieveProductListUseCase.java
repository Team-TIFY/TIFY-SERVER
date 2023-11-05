package tify.server.api.product.service;


import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.common.slice.SliceResponse;
import tify.server.core.annotation.UseCase;
import tify.server.domain.common.util.SliceUtil;
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
        List<ProductRetrieveDTO> products = new ArrayList<>();
        smallCategory.forEach(
                category -> {
                    Long categoryId = favorQuestionAdaptor.queryBySmallCategory(category).getId();
                    List<ProductRetrieveDTO> list =
                            productAdaptor
                                    .searchBySmallCategoryId(
                                            new ProductCategoryCondition(categoryId, pageable))
                                    .stream()
                                    .toList();
                    products.addAll(list);
                });
        return SliceResponse.of(SliceUtil.valueOf(products, pageable));
    }
}
