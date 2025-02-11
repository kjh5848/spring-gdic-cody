package co.kr.nomadlab.springgdic.selenium.page.admin.page;

import co.kr.nomadlab.springgdic.consts.SeleniumConst;
import co.kr.nomadlab.springgdic.selenium.page.Base;
import co.kr.nomadlab.springgdic.selenium.page.admin.component.AdminLoginComponent;

import java.io.IOException;

public class AdminLoginPage extends Base {

    private final AdminLoginComponent loginComponent;

    public void screenShot(String pathname) throws IOException {
        takeScreenshot(pathname);
    }

    public AdminLoginPage() {
        super();
        this.loginComponent = new AdminLoginComponent(wait, driver);
    }

    public void goLoginPage() {
        this.driver.get(SeleniumConst.adminUrl);
    }

    public AdminLoginComponent getLoginComponent() {
        return loginComponent;
    }

    public void close(){
        this.driver.quit();
    }

}
