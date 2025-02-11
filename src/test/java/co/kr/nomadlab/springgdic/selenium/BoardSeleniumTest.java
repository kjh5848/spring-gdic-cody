package co.kr.nomadlab.springgdic.selenium;

import co.kr.nomadlab.springgdic.selenium.page.board.page.BoardPage;
import com.google.common.util.concurrent.Uninterruptibles;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BoardSeleniumTest extends SpringBaseTestNGTest {

    private final BoardPage boardPage = new BoardPage();


    @Test
    public void boardSeleniumTest() throws IOException {
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        this.boardPage.goTo();
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        this.boardPage.getBoardComponent().getNotice();
        boardPage.screenShot("3-1 BoardList");
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        this.boardPage.getBoardComponent().getDetail();
        boardPage.screenShot("3-2 BoardList");
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

        this.boardPage.close();
    }

}