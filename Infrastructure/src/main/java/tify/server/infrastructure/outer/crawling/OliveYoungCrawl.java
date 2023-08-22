package tify.server.infrastructure.outer.crawling;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;
import tify.server.infrastructure.exception.FeignException;

@Component
@RequiredArgsConstructor
public class OliveYoungCrawl {

    private WebDriver driver;
    private static final String url = "https://www.oliveyoung.co.kr/store/G.do?goodsNo=";

    public Map<String, String> process(List<String> productCodes) {
        System.setProperty(
                "webdriver.chrome.driver",
                "/Users/sehwan/Downloads/chromedriver-mac-arm64/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);

        Map<String, String> dataList = new HashMap<>();
        try {
            dataList.putAll(getDataList(productCodes));
        } catch (InterruptedException e) {
            throw FeignException.EXCEPTION;
        }

        driver.close();
        driver.quit();

        return dataList;
    }

    private Map<String, String> getDataList(List<String> productCodes) throws InterruptedException {
        Map<String, String> map = new HashMap<>();
        Thread.sleep(1000);

        productCodes.forEach(
                productCode -> {
                    driver.get(url + productCode);

                    String prdName = driver.findElement(By.className("prd_name")).getText();
                    String imgSrc = driver.findElement(By.id("mainImg")).getAttribute("src");
                    map.put(prdName, imgSrc);
                });

        return map;
    }
}
