package tify.server.domain.domains.product.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.question.domain.FavorQuestionCategory;

@Getter
@AllArgsConstructor
@Builder
public class ProductRetrieveDTO {

    private Product product;

    private FavorQuestionCategory favorQuestionCategory;

    public static ProductRetrieveDTO of(Product product,
        FavorQuestionCategory favorQuestionCategory) {
        return ProductRetrieveDTO.builder().product(product)
            .favorQuestionCategory(favorQuestionCategory).build();
    }
}
