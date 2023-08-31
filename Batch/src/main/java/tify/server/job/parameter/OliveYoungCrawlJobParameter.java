package tify.server.job.parameter;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.beans.factory.annotation.Value;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.Product;
import tify.server.domain.domains.product.domain.Site;

@Getter
public class OliveYoungCrawlJobParameter {

    public OliveYoungCrawlJobParameter(ProductAdaptor productAdaptor) {
        this.productAdaptor = productAdaptor;
    }

    private ProductAdaptor productAdaptor;
    private List<Product> products = new ArrayList<>();

    @Value("#{jobParameters[siteUrl]}")
    public void setDate(String siteUrl) throws JobParametersInvalidException {
        if (Objects.isNull(siteUrl)) {
            throw new JobParametersInvalidException("이벤트 아이디가 필요합니다.");
        }
        this.products = productAdaptor.queryAllBySite(Site.toEnum(siteUrl));
    }
}
