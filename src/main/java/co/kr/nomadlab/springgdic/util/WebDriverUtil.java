package co.kr.nomadlab.springgdic.util;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.TimeUnit;
 
@Slf4j
public class WebDriverUtil {
 
    protected WebDriver driver;
    public static String WEB_DRIVER_ID = "webdriver.chrome.driver"; // Properties 설정
    public static String WEB_DRIVER_PATH = "C:/Users/HoHyeon Kim/Downloads/chromedriver_win32/chromedriver.exe"; // WebDriver 경로
 
    public WebDriverUtil() {
        chrome();
    }
 
    private void chrome() {
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
 
        // webDriver 옵션 설정.
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--lang=ko");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.setCapability("ignoreProtectedModeSettings", true);
 
        // weDriver 생성.
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
    }
 
    public void useDriver(String url) {
        driver.get(url) ;
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);  // 페이지 불러오는 여유시간.
        log.info("++++++++++++++++++++++===================+++++++++++++ selenium : " + driver.getTitle());
 
 
        WebElement searchLabel = driver.findElement(By.id("label-text"));
        log.info("++++++++++++++++++++++===================+++++++++++++ searchLabel : " + searchLabel.getText());
 
        quitDriver();
    }
 
    private void quitDriver() {
        driver.quit(); // webDriver 종료
    }
 
}