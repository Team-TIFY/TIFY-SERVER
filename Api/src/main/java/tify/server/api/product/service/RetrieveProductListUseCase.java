package tify.server.api.product.service;

import static tify.server.domain.domains.question.strategy.StrategyName.*;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.common.slice.SliceResponse;
import tify.server.api.config.security.SecurityUtils;
import tify.server.api.product.model.dto.ProductFilterCondition;
import tify.server.api.product.model.vo.ProductRetrieveVo;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.PriceFilter;
import tify.server.domain.domains.product.domain.PriceOrder;
import tify.server.domain.domains.product.dto.ProductCategoryCondition;
import tify.server.domain.domains.product.dto.ProductRetrieveDTO;
import tify.server.domain.domains.question.adaptor.FavorQuestionAdaptor;
import tify.server.domain.domains.question.domain.FavorQuestionCategory;
import tify.server.domain.domains.question.strategy.ProductRecommendationStrategy;
import tify.server.domain.domains.question.strategy.ProductRecommendationStrategyFactory;

@UseCase
@RequiredArgsConstructor
public class RetrieveProductListUseCase {

    private final ProductAdaptor productAdaptor;
    private final FavorQuestionAdaptor favorQuestionAdaptor;
    private final ProductRecommendationStrategyFactory strategyFactory;

    @Transactional(readOnly = true)
    public SliceResponse<ProductRetrieveVo> executeToSmallCategory(
            ProductFilterCondition productFilterCondition, Pageable pageable) {
        List<Long> categoryIdList = new ArrayList<>();
        List<ProductRecommendationStrategy> strategies = new ArrayList<>();
        productFilterCondition
                .getSmallCategoryList()
                .forEach(
                        category -> {
                            categoryIdList.addAll(
                                    favorQuestionAdaptor.queryBySmallCategory(category).stream()
                                            .map(FavorQuestionCategory::getId)
                                            .toList());
                            strategies.addAll(strategyFactory.findStrategy(category));
                        });

        if (productFilterCondition.getPriceOrder().equals(PriceOrder.DEFAULT)
                && productFilterCondition.getPriceFilter().equals(PriceFilter.DEFAULT)) {
            List<ProductRetrieveDTO> list = new ArrayList<>();
            strategies.forEach(
                    strategy -> {
                        list.addAll(
                                strategy
                                        .recommendation(
                                                SecurityUtils.getCurrentUserId(),
                                                strategy.getStrategyName().getValue())
                                        .stream()
                                        .map(
                                                product -> {
                                                    return ProductRetrieveDTO.of(
                                                            product,
                                                            favorQuestionAdaptor.queryCategory(
                                                                    product
                                                                            .getFavorQuestionCategoryId()));
                                                })
                                        .toList());
                    });
            return SliceResponse.of(
                    new SliceImpl<>(
                            list.stream().map(ProductRetrieveVo::from).toList(), pageable, true));
        } else {
            Slice<ProductRetrieveDTO> productRetrieveDTOS =
                    productAdaptor.searchBySmallCategoryId(
                            new ProductCategoryCondition(
                                    categoryIdList,
                                    productFilterCondition.getPriceOrder(),
                                    productFilterCondition.getPriceFilter(),
                                    pageable));

            return SliceResponse.of(
                    new SliceImpl<>(
                            productRetrieveDTOS.stream().map(ProductRetrieveVo::from).toList(),
                            pageable,
                            true));
        }
    }
}
