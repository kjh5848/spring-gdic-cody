package co.kr.nomadlab.springgdic.selenium.page;

import co.kr.nomadlab.springgdic.selenium.config.WebDriverConfig;
import jakarta.annotation.PostConstruct;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class BaseComponent {
    public abstract boolean isAt();

}