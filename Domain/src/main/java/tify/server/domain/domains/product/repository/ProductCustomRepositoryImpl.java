package tify.server.domain.domains.product.repository;

import static tify.server.domain.domains.product.domain.QProduct.*;
import static tify.server.domain.domains.question.domain.QFavorQuestionCategory.favorQuestionCategory;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.product.dto.ProductCrawlingDto;
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
}
