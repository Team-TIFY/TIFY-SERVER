package tify.server.api.product.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import tify.server.api.common.slice.SliceResponse;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.dto.ProductCondition;
import tify.server.domain.domains.product.dto.ProductRetrieveDTO;

@UseCase
@RequiredArgsConstructor
public class ProductSearchUseCase {

    private final ProductAdaptor productAdaptor;

    public SliceResponse<ProductRetrieveDTO> execute(String keyword, Pageable pageable) {
        ProductCondition productCondition = new ProductCondition(keyword, pageable);
        Slice<ProductRetrieveDTO> products = productAdaptor.searchByKeyword(productCondition);
        return SliceResponse.of(products);
    }
}
