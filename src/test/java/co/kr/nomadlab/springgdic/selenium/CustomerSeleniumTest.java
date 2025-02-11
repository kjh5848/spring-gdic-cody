package co.kr.nomadlab.springgdic.selenium;

import co.kr.nomadlab.springgdic.selenium.page.board.page.CustomerPage;
import com.google.common.util.concurrent.Uninterruptibles;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class CustomerSeleniumTest extends SpringBaseTestNGTest {

    private final CustomerPage customerPage = new CustomerPage();

    @Test
    @DisplayName("고객문의 등록 테스트 성공")
    void saveCustomer() throws IOException {
        customerPage.goTo();
        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);
        Assertions.assertTrue(this.customerPage.getCustomerComponent().isAt());

        customerPage.screenShot("1-1 CustomerSave");

        customerPage.getCustomerComponent().customerEnter("회사명");
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        customerPage.getCustomerComponent().customerUser("담당자명");
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        customerPage.getCustomerComponent().customerTell("010-1234-5678");
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        customerPage.getCustomerComponent().customerAddress("부산광역시 연제구");
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        customerPage.getCustomerComponent().customerContent("문의내용");
        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        customerPage.getCustomerComponent().customerRadio();

        customerPage.screenShot("1-2 CustomerSave");

        Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
        customerPage.getCustomerComponent().customerSave();

        Uninterruptibles.sleepUninterruptibly(2, TimeUnit.SECONDS);

        customerPage.close();

    }
}
