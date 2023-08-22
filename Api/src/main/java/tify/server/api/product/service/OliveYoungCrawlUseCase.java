package tify.server.api.product.service;


import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import tify.server.core.annotation.UseCase;
import tify.server.infrastructure.outer.crawling.OliveYoungCrawl;

@UseCase
@RequiredArgsConstructor
public class OliveYoungCrawlUseCase {

    private final OliveYoungCrawl oliveYoungCrawl;

    @Transactional
    public void execute(List<String> productCodes) {
        Map<String, String> process = oliveYoungCrawl.process(productCodes);
    }
}
