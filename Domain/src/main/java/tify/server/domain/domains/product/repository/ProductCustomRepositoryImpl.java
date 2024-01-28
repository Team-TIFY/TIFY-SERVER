package tify.server.domain.domains.product.repository;

import static java.lang.Long.*;
import static tify.server.domain.domains.product.domain.PriceFilter.*;
import static tify.server.domain.domains.product.domain.PriceOrder.*;
import static tify.server.domain.domains.product.domain.QProduct.*;
import static tify.server.domain.domains.question.domain.QFavorQuestionCategory.favorQuestionCategory;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import tify.server.domain.common.util.SliceUtil;
import tify.server.domain.domains.product.domain.PriceFilter;
import tify.server.domain.domains.product.domain.PriceOrder;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.product.domain.Site;
import tify.server.domain.domains.product.dto.condition.ProductCategoryCondition;
import tify.server.domain.domains.product.dto.condition.ProductCondition;
import tify.server.domain.domains.product.dto.model.ProductCrawlingDto;
import tify.server.domain.domains.product.dto.model.ProductRetrieveDto;
import tify.server.domain.domains.product.dto.model.ProductVo;

@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductCrawlingDto> search() {
        return queryFactory
                .select(
                        Projections.constructor(
                                ProductCrawlingDto.class, product.name, product.crawlUrl))
                .from(product)
                .groupBy(product.name)
                .fetch();
    }

    @Override
    public List<ProductCrawlingDto> searchByCompany(Site site) {
        return queryFactory
                .select(
                        Projections.constructor(
                                ProductCrawlingDto.class, product.name, product.crawlUrl))
                .from(product)
                .where(product.crawlUrl.contains(site.getValue()), product.imageUrl.isNull())
                .groupBy(product.name)
                .fetch();
    }

    @Override
    public List<Product> searchAllToRecommendation(String categoryName, String answer) {
        return queryFactory
                .selectFrom(product)
                .innerJoin(favorQuestionCategory)
                .on(
                        favorQuestionCategory.id.eq(product.favorQuestionCategoryId),
                        favorQuestionCategory.name.eq(categoryName))
                .where(product.characteristic.contains(answer))
                .fetch();
    }

    @Override
    public Slice<ProductVo> searchByKeyword(ProductCondition productCondition) {
        List<ProductVo> products =
                queryFactory
                        .select(
                                Projections.constructor(
                                        ProductVo.class,
                                        product.id,
                                        product.name,
                                        product.brand,
                                        product.characteristic,
                                        product.price,
                                        product.productOption,
                                        product.imageUrl,
                                        product.crawlUrl))
                        .from(product)
                        .where(product.name.contains(productCondition.getKeyword()))
                        .orderBy(product.id.asc())
                        .offset(productCondition.getPageable().getOffset())
                        .limit(productCondition.getPageable().getPageSize() + 1)
                        .fetch();

        return SliceUtil.valueOf(products, productCondition.getPageable());
    }

    @Override
    public Slice<ProductRetrieveDto> searchBySmallCategory(
            ProductCategoryCondition productCategoryCondition) {
        List<ProductRetrieveDto> products =
                queryFactory
                        .select(
                                Projections.constructor(
                                        ProductRetrieveDto.class, product, favorQuestionCategory))
                        .from(product)
                        .join(favorQuestionCategory)
                        .on(product.favorQuestionCategoryId.eq(favorQuestionCategory.id))
                        .where(
                                product.favorQuestionCategoryId.in(
                                        productCategoryCondition.getCategoryIdList()),
                                priceBetween(productCategoryCondition.getPriceFilter()))
                        .orderBy(orderByPrice(productCategoryCondition.getPriceOrder()))
                        .offset(productCategoryCondition.getPageable().getOffset())
                        .limit(productCategoryCondition.getPageable().getPageSize() + 1)
                        .fetch();
        return SliceUtil.valueOf(products, productCategoryCondition.getPageable());
    }

    @Override
    public List<ProductRetrieveDto> findAllBySmallCategory(
            ProductCategoryCondition productCategoryCondition) {
        return queryFactory
                .select(
                        Projections.constructor(
                                ProductRetrieveDto.class, product, favorQuestionCategory))
                .from(product)
                .join(favorQuestionCategory)
                .on(product.favorQuestionCategoryId.eq(favorQuestionCategory.id))
                .where(
                        product.favorQuestionCategoryId.in(
                                productCategoryCondition.getCategoryIdList()),
                        priceBetween(productCategoryCondition.getPriceFilter()))
                .orderBy(orderByPrice(productCategoryCondition.getPriceOrder()))
                .offset(productCategoryCondition.getPageable().getOffset())
                .limit(productCategoryCondition.getPageable().getPageSize() + 1)
                .fetch();
    }

    @Override
    public List<Product> searchAllByCategoryName(String categoryName) {
        return queryFactory
                .selectFrom(product)
                .innerJoin(favorQuestionCategory)
                .on(
                        favorQuestionCategory.id.eq(product.favorQuestionCategoryId),
                        favorQuestionCategory.name.eq(categoryName))
                .fetch();
    }

    private OrderSpecifier[] orderByPrice(PriceOrder priceOrder) {
        List<OrderSpecifier> orderSpecifiers = new ArrayList<>();
        if (priceOrder.equals(PRICE_ASC)) {
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, product.price));
        } else if (priceOrder.equals(PRICE_DESC)) {
            orderSpecifiers.add(new OrderSpecifier(Order.DESC, product.price));
        } else {
            orderSpecifiers.add(new OrderSpecifier(Order.ASC, product.id));
        }
        return orderSpecifiers.toArray(new OrderSpecifier[orderSpecifiers.size()]);
    }

    private BooleanExpression priceBetween(PriceFilter priceFilter) {
        if (priceFilter.equals(LESS_THAN_10000)) {
            return product.price.between(0L, 9999L);
        } else if (priceFilter.equals(MORE_THAN_10000_LESS_THAN_30000)) {
            return product.price.between(10000L, 29999L);
        } else if (priceFilter.equals(MORE_THAN_30000_LESS_THAN_50000)) {
            return product.price.between(30000L, 49999L);
        } else if (priceFilter.equals(MORE_THAN_50000)) {
            return product.price.between(50000L, MAX_VALUE);
        } else {
            return product.price.between(0L, MAX_VALUE);
        }
    }
}
