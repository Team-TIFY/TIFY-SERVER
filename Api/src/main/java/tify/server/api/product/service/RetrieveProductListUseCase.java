package tify.server.api.product.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.product.model.dto.ProductFilterCondition;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.PriceFilter;
import tify.server.domain.domains.product.domain.PriceOrder;
import tify.server.domain.domains.product.domain.Product;
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
    public List<ProductRetrieveDTO> executeToSmallCategory(
            ProductFilterCondition productFilterCondition) {
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
        List<Product> results =
                productAdaptor.findAllBySmallCategoryId(
                        new ProductCategoryCondition(
                                categoryIdList,
                                productFilterCondition.getPriceOrder(),
                                productFilterCondition.getPriceFilter(),
                                null));
        if (productFilterCondition.getPriceOrder().equals(PriceOrder.DEFAULT)
                || productFilterCondition.getPriceFilter().equals(PriceFilter.DEFAULT)) {
            Collections.shuffle(results);
        }
        return results.stream().map(ProductRetrieveDTO::from).toList();
    }
}
