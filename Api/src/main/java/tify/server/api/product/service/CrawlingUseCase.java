package tify.server.api.product.service;


import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.dto.ProductCrawlingDto;
import tify.server.infrastructure.outer.crawling.OliveYoungCrawl;

@UseCase
@RequiredArgsConstructor
public class CrawlingUseCase {

    private final OliveYoungCrawl oliveYoungCrawl;
    private final ProductAdaptor productAdaptor;

    @Transactional
    public void executeForOliveYoung() {
        List<ProductCrawlingDto> productCrawlingDtos = productAdaptor.searchByName();
        productCrawlingDtos.forEach(
                dto -> {
                    String imgSrc = oliveYoungCrawl.process(dto.getCrawlUrl());
                    updateImageUrl(dto.getName(), imgSrc);
                });
    }

    @Transactional
    public void updateImageUrl(String name, String imgSrc) {
        productAdaptor
                .queryAllByName(name)
                .forEach(
                        product -> {
                            product.updateImageUrl(imgSrc);
                        });
    }
}
