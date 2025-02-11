package co.kr.nomadlab.springgdic.selenium.page.board.page;


import co.kr.nomadlab.springgdic.consts.SeleniumConst;
import co.kr.nomadlab.springgdic.selenium.page.Base;
import co.kr.nomadlab.springgdic.selenium.page.board.component.BoardComponent;
import org.openqa.selenium.By;

import java.io.IOException;


public class BoardPage extends Base {

    private final BoardComponent boardComponent;

    public void screenShot(String pathname) throws IOException {
        takeScreenshot(pathname);
    }

    public BoardPage() {
        super();
        this.boardComponent = new BoardComponent(wait, driver);
    }

    public void goTo() {
        this.driver.get(SeleniumConst.url);
    }

    public void xpath(String xpath){
        this.driver.findElement(By.xpath(xpath)).click();
    }

    public void link(String link){
        this.driver.findElement(By.linkText(link)).click();
    }

    public BoardComponent getBoardComponent() {
        return boardComponent;
    }

    public void close(){
        this.driver.quit();
    }
}
