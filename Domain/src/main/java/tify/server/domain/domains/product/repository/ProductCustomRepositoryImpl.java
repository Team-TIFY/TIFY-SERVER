package tify.server.domain.domains.product.repository;

import static tify.server.domain.domains.product.domain.QProduct.*;
import static tify.server.domain.domains.question.domain.QFavorQuestionCategory.favorQuestionCategory;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import tify.server.domain.common.util.SliceUtil;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.product.domain.Site;
import tify.server.domain.domains.product.dto.ProductCondition;
import tify.server.domain.domains.product.dto.ProductCrawlingDto;
import tify.server.domain.domains.product.dto.ProductRetrieveDTO;
import tify.server.domain.domains.product.dto.QProductCrawlingDto;

@RequiredArgsConstructor
public class ProductCustomRepositoryImpl implements ProductCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductCrawlingDto> search() {
        return queryFactory
                .select(new QProductCrawlingDto(product.name, product.crawlUrl))
                .from(product)
                .groupBy(product.name)
                .fetch();
    }

    @Override
    public List<ProductCrawlingDto> searchByCompany(Site site) {
        return queryFactory
                .select(new QProductCrawlingDto(product.name, product.crawlUrl))
                .from(product)
                .where(product.crawlUrl.contains(site.getValue()))
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
    public Slice<ProductRetrieveDTO> searchByKeyword(ProductCondition productCondition) {
        List<ProductRetrieveDTO> products =
                queryFactory
                        .select(
                                Projections.constructor(
                                        ProductRetrieveDTO.class,
                                        product.id,
                                        product.name,
                                        product.brand,
                                        product.characteristic,
                                        product.price))
                        .from(product)
                        .where(product.name.contains(productCondition.getKeyword()))
                        .orderBy(product.id.asc())
                        .offset(productCondition.getPageable().getOffset())
                        .limit(productCondition.getPageable().getPageSize() + 1)
                        .fetch();

        return SliceUtil.valueOf(products, productCondition.getPageable());
    }
}
