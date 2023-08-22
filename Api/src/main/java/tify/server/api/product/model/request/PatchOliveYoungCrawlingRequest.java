package tify.server.api.product.model.request;


import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PatchOliveYoungCrawlingRequest {

    private List<String> productCodes;
}
