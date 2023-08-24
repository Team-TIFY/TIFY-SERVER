package tify.server.infrastructure.outer.crawling;


import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;
import tify.server.infrastructure.exception.FeignException;

@Component
@RequiredArgsConstructor
public class OliveYoungCrawl {

    private WebDriver driver;

    public String process(String url) {
        System.setProperty(
                "webdriver.chrome.driver",
                "/Users/sehwan/Downloads/chromedriver-mac-arm64/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);

        String imgSrc = "";

        try {
            imgSrc = getDataList(url);
        } catch (InterruptedException e) {
            throw FeignException.EXCEPTION;
        }

        driver.close();
        driver.quit();

        return imgSrc;
    }

    private String getDataList(String url) throws InterruptedException {
        driver.get(url);
        Thread.sleep(1000);
        WebElement element = driver.findElement(By.id("mainImg"));
        System.out.println(element.toString());
        return element.getAttribute("src");
    }
}
