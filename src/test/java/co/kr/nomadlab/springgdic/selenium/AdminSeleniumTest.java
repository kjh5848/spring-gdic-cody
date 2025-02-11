package co.kr.nomadlab.springgdic.selenium;

import co.kr.nomadlab.springgdic.selenium.page.admin.page.AdminBoardSavePage;
import co.kr.nomadlab.springgdic.selenium.page.admin.page.AdminLoginPage;
import co.kr.nomadlab.springgdic.selenium.page.admin.page.AdminPopupPage;
import com.google.common.util.concurrent.Uninterruptibles;
import org.junit.jupiter.api.Assertions;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AdminSeleniumTest extends SpringBaseTestNGTest {
    @Test
    public void AdminLoginTest() throws IOException {
        AdminLoginPage adminLoginPage = new AdminLoginPage();
        loginAction(adminLoginPage);
        adminLoginPage.close();
    }

    private void loginAction(AdminLoginPage adminLoginPage) throws IOException {
        adminLoginPage.goLoginPage();
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        Assertions.assertTrue(adminLoginPage.getLoginComponent().isAt());
        adminLoginPage.screenShot("2-1 AdminLogin");
        adminLoginPage.getLoginComponent().loginId("admin");
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        adminLoginPage.getLoginComponent().loginPassword("!QW@1qw2");
        adminLoginPage.screenShot("2-2 AdminLogin");
        adminLoginPage.getLoginComponent().login();


        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

    }


    @Test
    public void AdminBoardTest() throws Exception {
        AdminBoardSavePage adminBoardSavePage = new AdminBoardSavePage();

        // 어드민 로그인
        loginAction(adminBoardSavePage);
        adminBoardSavePage.screenShot("2-4 AdminBoardSave");
        // (공지사항)클릭 후 (공지사항 등록) 클릭 해서 이동
        adminBoardSavePage.getBoardComponent().noticeClick();
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

        adminBoardSavePage.screenShot("2-5 AdminBoardSave");
        // 제목 및 내용 입력
        Assertions.assertTrue(adminBoardSavePage.getBoardComponent().isAt());
        adminBoardSavePage.getBoardComponent().boardTitle("title test 코드 작성");
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        adminBoardSavePage.getBoardComponent().boardContent("content test 코드 작성");
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

        // 파일 업로드
        adminBoardSavePage.getBoardComponent().fileUpload("C:\\Users\\HoHyeon Kim\\Desktop\\다운로드.png");
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

        adminBoardSavePage.screenShot("2-6 AdminBoardSave");

        // 등록 버튼 클릭
        adminBoardSavePage.getBoardComponent().saveButtons();

        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

        adminBoardSavePage.close();

    }

//    @Test
//    public void AdminSupplyTest() throws Exception {
//        AdminSupplySavePage adminSupplySavePage = new AdminSupplySavePage();
//
//        // 어드민 로그인
//        loginAction(adminSupplySavePage);
//
//        // (공급공고)클릭 후 (공급공고 등록) 클릭 해서 이동
//        adminSupplySavePage.getSupplyComponent().noticeClick();
//        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
//
//        Assertions.assertTrue(adminSupplySavePage.getSupplyComponent().isAt());
//        adminSupplySavePage.getSupplyComponent().boardTitle("title test 코드 작성");
//        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
//        adminSupplySavePage.getSupplyComponent().boardContent("content test 코드 작성");
//        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
//
//        // 파일 업로드
//        adminSupplySavePage.getSupplyComponent().fileUpload("C:\\Users\\HoHyeon Kim\\Desktop\\다운로드.png");
//        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
//
//        // 등록 버튼 클릭
//        adminSupplySavePage.getSupplyComponent().saveButtons();
//
//    }

    @Test
    public void AdminPopupTest() throws IOException {
        AdminPopupPage adminPopupPage = new AdminPopupPage();

        loginAction(adminPopupPage);

        adminPopupPage.screenShot("2-7 AdminPopupSave");
        // 팝업관리로 클릭 후 이동
        adminPopupPage.getPopupComponent().popupClick();
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

        adminPopupPage.screenShot("2-8 AdminPopupSave");

        Assertions.assertTrue(adminPopupPage.getPopupComponent().isAt());
        // 팝업 이미지 업로드
        adminPopupPage.getPopupComponent().fileUpload("C:\\Users\\HoHyeon Kim\\Desktop\\다운로드.png");
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

        // 원래 팝업 링크 지우고 새로운 링크 입력
        adminPopupPage.getPopupComponent().setPopupLink("https://www.gdicland.co.kr/");
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

        adminPopupPage.screenShot("2-9 AdminPopupSave");

        // 등록 버튼 클릭
        adminPopupPage.getPopupComponent().saveButtons();

        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

        adminPopupPage.close();

    }
}
