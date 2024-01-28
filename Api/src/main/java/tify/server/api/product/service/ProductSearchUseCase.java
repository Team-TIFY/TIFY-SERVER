package tify.server.api.product.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;
import tify.server.api.common.slice.SliceResponse;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.dto.condition.ProductCondition;
import tify.server.domain.domains.product.dto.model.ProductVo;

@UseCase
@RequiredArgsConstructor
public class ProductSearchUseCase {

    private final ProductAdaptor productAdaptor;

    @Transactional(readOnly = true)
    public SliceResponse<ProductVo> execute(String keyword, Pageable pageable) {
        ProductCondition productCondition = new ProductCondition(keyword, pageable);
        Slice<ProductVo> products = productAdaptor.searchByKeyword(productCondition);
        return SliceResponse.of(products);
    }
}
