package tify.server.infrastructure.outer.crawling;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TwentyNineCrawl {
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
            //            throw FeignException.EXCEPTION;
            log.info("에러 발생 ㅠㅠ");
        } catch (UnhandledAlertException e) {
            log.info("유효하지 않은 url : {}", url);
        } finally {
            driver.close();
            driver.quit();

            return imgSrc;
        }
    }

    private String getDataList(String url) throws InterruptedException {
        driver.get(url);
        Thread.sleep(1000);
        if (!driver.findElements(By.cssSelector(".css-12qah06.ewptmlp5")).isEmpty()) {
            WebElement element =
                    driver.findElements(By.cssSelector(".css-12qah06.ewptmlp5")).get(0);
            System.out.println(element.toString());
            return element.getAttribute("src");
        }
        return null;
    }
}
