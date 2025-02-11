package co.kr.nomadlab.springgdic.htmlUnit;

import com.gargoylesoftware.htmlunit.WebClient;

public class BaseUnit {
    protected WebClient webClient = new WebClient();
    protected String baseUrl = "http://localhost:8080";
}
