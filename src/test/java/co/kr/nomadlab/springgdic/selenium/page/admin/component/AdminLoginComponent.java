package co.kr.nomadlab.springgdic.selenium.page.admin.component;

import co.kr.nomadlab.springgdic.selenium.page.BaseComponent;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AdminLoginComponent extends BaseComponent {

    private final WebDriverWait wait;


    @FindBy(name="username")
    private WebElement usernameBox;

    @FindBy(name="password")
    private WebElement passwordBox;

    @FindBy(name="save")
    private List<WebElement> saveButtons;

    public AdminLoginComponent(WebDriverWait wait, WebDriver driver) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void loginId(String keyword){
        usernameBox.sendKeys(keyword);
        usernameBox.sendKeys(Keys.TAB);
    }

    public void loginPassword(final String keyword){
        this.passwordBox.sendKeys(keyword);
        this.passwordBox.sendKeys(Keys.TAB);
    }

    public void login() {
        this.saveButtons
                .stream()
                .filter(e -> e.isDisplayed() && e.isEnabled())
                .findFirst()
                .ifPresent(WebElement::click);
    }

    @Override
    public boolean isAt() {
        return this.wait.until(
                (d) -> usernameBox.isDisplayed()
                        && this.passwordBox.isDisplayed()
                        && !this.saveButtons.isEmpty()
        );
    }

}