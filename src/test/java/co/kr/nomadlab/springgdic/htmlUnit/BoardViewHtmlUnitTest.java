package co.kr.nomadlab.springgdic.htmlUnit;

import com.gargoylesoftware.htmlunit.html.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardViewHtmlUnitTest extends BaseUnit {

    @Test
    @DisplayName("공지사항 성공 (Html Unit)")
    void boardNotice() throws Exception{

        final HtmlPage page = webClient.getPage( baseUrl + "/board/notice");

        HtmlElement form = page.getHtmlElementById("gdic_main");
        Assertions.assertNotNull(form);

        HtmlElement body = page.getHtmlElementById("gdic_body");
        String canonicalXPath = body.getCanonicalXPath();
        System.out.println("테스트 : " + canonicalXPath);

        HtmlHeading2 h2 = page.getFirstByXPath("//h2");
        Assertions.assertEquals("공지사항", h2.getTextContent());

        HtmlTable table = page.getFirstByXPath("//table");
        Assertions.assertNotNull(table);

        HtmlTableHeader header = table.getHeader();
        Assertions.assertNotNull(header);

    }
}
