package co.kr.nomadlab.springgdic.selenium.page.admin.page;

import co.kr.nomadlab.springgdic.consts.SeleniumConst;
import co.kr.nomadlab.springgdic.selenium.page.admin.component.AdminSaveBoardComponent;

import java.io.IOException;

public class AdminBoardSavePage extends AdminLoginPage {

    private final AdminSaveBoardComponent boardComponent;

    public void screenShot(String pathname) throws IOException {
        takeScreenshot(pathname);
    }

    public AdminBoardSavePage() {
        super();
        this.boardComponent = new AdminSaveBoardComponent(wait, driver);
    }

    public void goPage(){
        this.driver.get(SeleniumConst.adminUrl);
    }

    public AdminSaveBoardComponent getBoardComponent() {
        return boardComponent;
    }

}
