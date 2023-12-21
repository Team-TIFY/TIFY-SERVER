package tify.server.api.product.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.common.slice.SliceResponse;
import tify.server.api.product.model.dto.ProductFilterCondition;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.PriceFilter;
import tify.server.domain.domains.product.domain.PriceOrder;
import tify.server.domain.domains.product.dto.ProductCategoryCondition;
import tify.server.domain.domains.product.dto.ProductRetrieveDTO;
import tify.server.domain.domains.question.adaptor.FavorQuestionAdaptor;
import tify.server.domain.domains.question.domain.FavorQuestionCategory;

@UseCase
@RequiredArgsConstructor
public class RetrieveProductListUseCase {

    private final ProductAdaptor productAdaptor;
    private final FavorQuestionAdaptor favorQuestionAdaptor;

    @Transactional(readOnly = true)
    public SliceResponse<ProductRetrieveDTO> executeToSmallCategory(
            ProductFilterCondition productFilterCondition, Pageable pageable) {
        List<Long> categoryIdList = new ArrayList<>();
        productFilterCondition
                .getSmallCategoryList()
                .forEach(
                        category -> {
                            categoryIdList.addAll(
                                    favorQuestionAdaptor.queryBySmallCategory(category).stream()
                                            .map(FavorQuestionCategory::getId)
                                            .toList());
                        });

        Slice<ProductRetrieveDTO> productRetrieveDTOS =
                productAdaptor.searchBySmallCategoryId(
                        new ProductCategoryCondition(
                                categoryIdList,
                                productFilterCondition.getPriceOrder(),
                                productFilterCondition.getPriceFilter(),
                                pageable));
        if (productFilterCondition.getPriceOrder().equals(PriceOrder.DEFAULT)
                && productFilterCondition.getPriceFilter().equals(PriceFilter.DEFAULT)) {
            List<ProductRetrieveDTO> result = productRetrieveDTOS.toList();
            Collections.shuffle(result);

            Slice<ProductRetrieveDTO> shuffledResult = new SliceImpl<>(result, pageable, true);
            return SliceResponse.of(shuffledResult);
        }
        return SliceResponse.of(productRetrieveDTOS);
    }
}
