package tify.server.job;


import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tify.server.domain.domains.product.adaptor.ProductAdaptor;
import tify.server.domain.domains.product.domain.Product;
import tify.server.infrastructure.outer.crawling.OliveYoungCrawl;
import tify.server.job.parameter.OliveYoungCrawlJobParameter;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CrawlingJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ProductAdaptor productAdaptor;
    private final OliveYoungCrawl oliveYoungCrawl;

    @Bean
    public OliveYoungCrawlJobParameter oliveYoungCrawlJobParameter() {
        return new OliveYoungCrawlJobParameter(productAdaptor);
    }

    @Bean
    public Job oliveYoungJob() {
        return jobBuilderFactory.get("oliveYoungJob").start(step()).build();
    }

    @Bean
    public Step step() {
        return stepBuilderFactory
                .get("step")
                .tasklet(
                        (contribution, chunkContext) -> {
                            List<Product> products = oliveYoungCrawlJobParameter().getProducts();
                            products.forEach(
                                    product -> {
                                        product.updateImageUrl(
                                                oliveYoungCrawl.process(product.getCrawlUrl()));
                                    });
                            return RepeatStatus.FINISHED;
                        })
                .build();
    }
}
