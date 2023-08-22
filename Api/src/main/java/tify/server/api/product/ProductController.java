package tify.server.api.product;


import io.swagger.v3.oas.annotations.Operation;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tify.server.api.product.model.request.PatchOliveYoungCrawlingRequest;
import tify.server.infrastructure.outer.crawling.OliveYoungCrawl;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final OliveYoungCrawl oliveYoungCrawl;

    @Operation(summary = "올리브영 크롤링을 통해 상품명과 상품 이미지를 가져옵니다.")
    @PatchMapping("/oliveyoung")
    public void oliveYoungCrawl(
            @RequestBody PatchOliveYoungCrawlingRequest patchOliveYoungCrawlingRequest) {
        Map<String, String> process =
                oliveYoungCrawl.process(patchOliveYoungCrawlingRequest.getProductCodes());
    }
}
