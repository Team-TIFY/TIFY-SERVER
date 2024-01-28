package tify.server.domain.domains.product.dto.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.question.domain.FavorQuestionCategory;

@Getter
@AllArgsConstructor
@Builder
public class ProductRetrieveDto {

    private Product product;

    private FavorQuestionCategory favorQuestionCategory;
}
