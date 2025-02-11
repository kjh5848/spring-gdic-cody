package co.kr.nomadlab.springgdic.selenium.page.board.page;

import co.kr.nomadlab.springgdic.consts.SeleniumConst;
import co.kr.nomadlab.springgdic.selenium.page.Base;
import co.kr.nomadlab.springgdic.selenium.page.board.component.CustomerComponent;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Lazy;

import java.io.IOException;

@Lazy
public class CustomerPage extends Base {

    public void screenShot(String pathname) throws IOException {
        takeScreenshot(pathname);
    }

    @Lazy
    private final CustomerComponent customerComponent;

    public CustomerPage() {
        super();
        this.customerComponent = new CustomerComponent(wait, driver);
    }

    public CustomerComponent getCustomerComponent() {
        return customerComponent;
    }

    public void goTo() {
        this.driver.get(SeleniumConst.url + "/board/registerCustomer");
    }

    public void xpath(String xpath){
        this.driver.findElement(By.xpath(xpath)).click();
    }

    public void link(String link){
        this.driver.findElement(By.linkText(link)).click();
    }


    public void close(){
        this.driver.quit();
    }
}
