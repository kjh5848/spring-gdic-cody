package co.kr.nomadlab.springgdic.module.gdic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gdic")
public class GdicViewController {

    // 인사말 페이지
    @GetMapping("greeting")
    public String greeting() {
        return "gdic/greeting";
    }

    // 회사소개 페이지
    @GetMapping("/introduction")
    public String introduction() {
        return "gdic/introduction";
    }

    // 협력기관 페이지
    @GetMapping("/organization")
    public String organization() {
        return "gdic/organization";
    }




}
