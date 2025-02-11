package co.kr.nomadlab.springgdic.selenium.page.board.component;


import co.kr.nomadlab.springgdic.selenium.page.BaseComponent;
import com.google.common.util.concurrent.Uninterruptibles;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BoardComponent extends BaseComponent {

    @FindBy(linkText = "게시판")
    private WebElement noticeLink;

    @FindBy(name = "boardList")
    private WebElement detailLink;

    private final WebDriverWait wait;

    public BoardComponent(WebDriverWait wait, WebDriver driver) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }


    public void getNotice(){
        this.noticeLink.click();
    }

    public void getDetail(){
        this.detailLink.click();
    }


    @Override
    public boolean isAt() {
        return this.wait.until((d) -> this.noticeLink.isDisplayed());
    }
}