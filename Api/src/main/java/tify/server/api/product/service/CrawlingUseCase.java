package tify.server.api.product.service;


import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.UseCase;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.entity.Product;
import tify.server.infrastructure.outer.crawling.OliveYoungCrawl;

@UseCase
@RequiredArgsConstructor
public class CrawlingUseCase {

    private final OliveYoungCrawl oliveYoungCrawl;
    private final ProductAdaptor productAdaptor;

    @Transactional
    public void executeForOliveYoung(List<String> productCodes) {
        Map<String, String> process = oliveYoungCrawl.process(productCodes);
        updateImageUrl(process);
    }

    @Transactional
    public void updateImageUrl(Map<String, String> crawls) {
        for (String productName : crawls.keySet()) {
            List<Product> products = productAdaptor.queryAllByName(productName);
            products.forEach(product -> product.updateImageUrl(crawls.get(productName)));
        }
    }
}
