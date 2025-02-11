package co.kr.nomadlab.springgdic.htmlUnit;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlHeading2;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerHtmlUnitTest extends BaseUnit {

    WebClient webClient = new WebClient();

    @Test
    @DisplayName("관심고객등록 성공 (Html Unit)")
    void boardCustomer() throws Exception {

        final HtmlPage page = webClient.getPage(baseUrl + "/customer/registerCustomer");

        HtmlTextInput enterName = page.getHtmlElementById("gdic_cname");
        enterName.setTextContent("회사명");

        HtmlTextInput userName = page.getHtmlElementById("gdic_uname");
        userName.setTextContent("담당자명");

        HtmlTextInput tell = page.getHtmlElementById("gdic_tell");
        tell.setTextContent("휴대전화");

        HtmlTextInput address = page.getHtmlElementById("gdic_address");
        address.setTextContent("주소");

        HtmlTextArea textInput = page.getHtmlElementById("gdic_content");
        textInput.setText("문의 내용 입니다.");

        assertThat(page.getUrl().toString()).endsWith( baseUrl + "/customer/registerCustomer");

        HtmlHeading2 h2 = page.getFirstByXPath("//h2");
        Assertions.assertEquals("관심고객등록", h2.getTextContent());

        String summary1 = page.getHtmlElementById("gdic_cname").getTextContent();
        assertThat(summary1).isEqualTo("회사명");
        String summary2 = page.getHtmlElementById("gdic_uname").getTextContent();
        assertThat(summary2).isEqualTo("담당자명");
        String summary3 = page.getHtmlElementById("gdic_tell").getTextContent();
        assertThat(summary3).isEqualTo("휴대전화");
        String summary4 = page.getHtmlElementById("gdic_address").getTextContent();
        assertThat(summary4).isEqualTo("주소");
        String text = page.getHtmlElementById("gdic_content").getTextContent();
        assertThat(text).isEqualTo("문의 내용 입니다.");
    }
}
