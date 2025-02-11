package co.kr.nomadlab.springgdic.selenium.page.admin.component;

import co.kr.nomadlab.springgdic.selenium.page.BaseComponent;
import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class AdminSaveSupplyComponent extends BaseComponent {

    @FindBy(name = "title")
    private WebElement titleBox;

    @FindBy(name = "content1")
    private WebElement contentBox;

    @FindBy(linkText = "공급공고")
    private WebElement noticeLink;

    @FindBy(linkText = "공급공고 등록")
    private WebElement getNoticeLink;

    @FindBy(name = "noticeFiles")
    private WebElement fileUpload;

    @FindBy(name = "save")
    private List<WebElement> saveButtons;

    private final WebDriverWait wait;

    public AdminSaveSupplyComponent(WebDriverWait wait, WebDriver driver) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void noticeClick(){
        this.noticeLink.click();
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        this.getNoticeLink.click();
    }
    public void boardTitle(final String keyword){
        this.titleBox.sendKeys(keyword);
        this.titleBox.sendKeys(Keys.TAB);
    }

    public void boardContent(final String keyword) {
        this.contentBox.sendKeys(keyword);
        this.contentBox.sendKeys(Keys.TAB);
        this.contentBox.sendKeys(Keys.ENTER);
    }
    public void fileUpload(final String keyword) {
        this.fileUpload.sendKeys(keyword);
    }

    public void saveButtons() {
        this.contentBox.sendKeys(Keys.ENTER);
        this.saveButtons
                .stream()
                .filter(e -> e.isDisplayed() && e.isEnabled())
                .findFirst()
                .ifPresent(WebElement::click);
    }

    @Override
    public boolean isAt() {
        return this.wait.until((d) -> this.titleBox.isDisplayed()
                && this.contentBox.isDisplayed()
                && !this.saveButtons.isEmpty()
        );
    }
}
