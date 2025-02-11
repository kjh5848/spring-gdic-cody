package co.kr.nomadlab.springgdic.selenium.page.admin.page;

import co.kr.nomadlab.springgdic.selenium.page.admin.component.AdminPopupComponent;

import java.io.IOException;

public class AdminPopupPage extends AdminLoginPage{

    private final AdminPopupComponent popupComponent;

    public void screenShot(String pathname) throws IOException {
        takeScreenshot(pathname);
    }

    public AdminPopupPage() {
        super();
        this.popupComponent = new AdminPopupComponent(wait, driver);
    }

    public AdminPopupComponent getPopupComponent() {
        return popupComponent;
    }
}
