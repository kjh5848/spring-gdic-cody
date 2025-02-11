package co.kr.nomadlab.springgdic.selenium.page.admin.component;

import co.kr.nomadlab.springgdic.selenium.page.BaseComponent;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AdminPopupComponent extends BaseComponent {

    @FindBy(linkText = "팝업관리")
    private WebElement popupLink;

    @FindBy(name = "file")
    private WebElement fileUpload;

    @FindBy(name = "link")
    private WebElement linkBox;

    @FindBy(name = "save")
    private List<WebElement> saveButtons;

    private final WebDriverWait wait;

    public AdminPopupComponent(WebDriverWait wait, WebDriver driver) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void popupClick(){
        this.popupLink.click();
    }

    public void fileUpload(final String keyword) {
        this.fileUpload.sendKeys(keyword);
    }

    public void setPopupLink(final String keyword) {
        this.linkBox.clear();
        this.linkBox.sendKeys(keyword);
    }
    public void saveButtons() {
        this.linkBox.sendKeys(Keys.ENTER);
        this.saveButtons
                .stream()
                .filter(e -> e.isDisplayed() && e.isEnabled())
                .findFirst()
                .ifPresent(WebElement::click);
    }


    @Override
    public boolean isAt() {
        return this.wait.until((d) -> this.linkBox.isDisplayed()
                && this.linkBox.isEnabled()
                && this.fileUpload.isDisplayed()
                && this.fileUpload.isEnabled()
                && !this.saveButtons.isEmpty()
        );
    }

}
