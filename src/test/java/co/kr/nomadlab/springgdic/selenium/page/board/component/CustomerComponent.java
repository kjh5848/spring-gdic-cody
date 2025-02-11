package co.kr.nomadlab.springgdic.selenium.page.board.component;

import co.kr.nomadlab.springgdic.selenium.page.BaseComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.util.List;

public class CustomerComponent extends BaseComponent {

    @FindBy(name = "enterpriseName")
    private WebElement enterpriseBox;

    @FindBy(name = "userName")
    private WebElement userBox;

    @FindBy(name = "tell")
    private WebElement tellBox;

    @FindBy(name = "address")
    private WebElement addressBox;

    @FindBy(name = "content")
    private WebElement contentBox;

    @FindAll({
            @FindBy(name = "m_4_4_radio"),
            @FindBy(id = "m_4_4_radio_1")
    })
    private WebElement radioInput;

    @FindBy(name = "saveButton")
    private WebElement saveButtons;

    private final WebDriverWait wait;

    public CustomerComponent(WebDriverWait wait, WebDriver driver) {
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public void customerEnter(String keyword){
        enterpriseBox.sendKeys(keyword);
        enterpriseBox.sendKeys(Keys.TAB);
    }

    public void customerUser(String keyword){
        this.userBox.sendKeys(keyword);
        this.userBox.sendKeys(Keys.TAB);
    }
    public void customerTell(String keyword){
        this.tellBox.sendKeys(keyword);
        this.tellBox.sendKeys(Keys.TAB);
    }
    public void customerAddress(String keyword){
        this.addressBox.sendKeys(keyword);
        this.addressBox.sendKeys(Keys.TAB);
    }
    public void customerContent(String keyword){
        this.contentBox.sendKeys(Keys.PAGE_DOWN);
        this.contentBox.sendKeys(keyword);
        this.addressBox.sendKeys(Keys.TAB);
    }


    public void customerRadio(){
        this.radioInput.click();
        this.addressBox.sendKeys(Keys.TAB);
    }

    public void customerSave(){
        this.saveButtons.click();
    }

    @Override
    public boolean isAt() {
        return this.wait.until(
                (d) -> this.enterpriseBox.isDisplayed()
                && this.userBox.isDisplayed()
                && this.tellBox.isDisplayed()
                && this.addressBox.isDisplayed()
                && this.contentBox.isDisplayed()
        );
    }
}
