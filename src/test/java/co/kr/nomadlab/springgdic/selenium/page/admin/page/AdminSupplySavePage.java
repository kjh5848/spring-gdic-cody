package co.kr.nomadlab.springgdic.selenium.page.admin.page;

import co.kr.nomadlab.springgdic.consts.SeleniumConst;
import co.kr.nomadlab.springgdic.selenium.page.admin.component.AdminSaveSupplyComponent;

import java.io.IOException;

public class AdminSupplySavePage extends AdminLoginPage {

    public void screenShot(String pathname) throws IOException {
        takeScreenshot(pathname);
    }

    private final AdminSaveSupplyComponent supplyComponent;

    public AdminSupplySavePage() {
        super();
        this.supplyComponent = new AdminSaveSupplyComponent(wait, driver);
    }

    public void goPage(){
        this.driver.get(SeleniumConst.adminUrl);
    }

    public AdminSaveSupplyComponent getSupplyComponent() {
        return supplyComponent;
    }

}
