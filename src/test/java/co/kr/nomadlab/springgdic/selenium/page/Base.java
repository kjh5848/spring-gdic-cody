package co.kr.nomadlab.springgdic.selenium.page;

import co.kr.nomadlab.springgdic.selenium.config.WebDriverConfig;
import jakarta.annotation.PostConstruct;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public abstract class Base {
    protected WebDriver driver = WebDriverConfig.chromeDriver();

    protected WebDriverWait wait = WebDriverConfig.webdriverWait(driver);

    protected void takeScreenshot(String pathname) throws IOException {

        Path path = Path.of("C:\\Users\\HoHyeon Kim\\IdeaProjects\\spring-gdic\\screenshot");

        File screenshotFile = ((TakesScreenshot) this.driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshotFile, path.resolve(pathname+ ".png").toFile());
    }


}