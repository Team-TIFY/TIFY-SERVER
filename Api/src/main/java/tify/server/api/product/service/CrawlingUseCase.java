package tify.server.api.product.service;


import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.Site;
import tify.server.domain.domains.product.dto.ProductCrawlingDto;
import tify.server.infrastructure.outer.crawling.MusinsaCrawl;
import tify.server.infrastructure.outer.crawling.OliveYoungCrawl;
import tify.server.infrastructure.outer.crawling.TwentyNineCrawl;

@UseCase
@RequiredArgsConstructor
public class CrawlingUseCase {

    private final OliveYoungCrawl oliveYoungCrawl;
    private final MusinsaCrawl musinsaCrawl;
    private final TwentyNineCrawl twentyNineCrawl;
    private final ProductAdaptor productAdaptor;

    @Transactional
    public void executeForOliveYoung() {
        List<ProductCrawlingDto> productCrawlingDtos =
                productAdaptor.searchByCompany(Site.OLIVE_YOUNG);
        productCrawlingDtos.forEach(
                dto -> {
                    String imgSrc = oliveYoungCrawl.process(dto.getCrawlUrl());
                    updateImageUrl(dto.getName(), imgSrc);
                });
    }

    @Transactional
    public void executeForMusinsa() {
        List<ProductCrawlingDto> productCrawlingDtos = productAdaptor.searchByCompany(Site.MUSINSA);
        productCrawlingDtos.forEach(
                dto -> {
                    System.out.println(dto.getName() + dto.getCrawlUrl());
                    String imgSrc = musinsaCrawl.process(dto.getCrawlUrl());
                    updateImageUrl(dto.getName(), imgSrc);
                });
    }

    @Transactional
    public void executeForTwentyNine() {
        List<ProductCrawlingDto> productCrawlingDtos = productAdaptor.searchByCompany(Site.CM);
        productCrawlingDtos.forEach(
                dto -> {
                    String imgSrc = twentyNineCrawl.process(dto.getCrawlUrl());
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
