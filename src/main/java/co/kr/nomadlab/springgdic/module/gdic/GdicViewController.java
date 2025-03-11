package co.kr.nomadlab.springgdic.module.gdic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RequestMapping("")
public class GdicViewController {

    // 인사말 페이지


    @GetMapping("/kseta/about")
    public String greeting2() {
        return "kseta/about";
    }

    @GetMapping("/kseta/edu")
    public String edu() {
        return "kseta/edu";
    }


    @GetMapping("/kseta/certificate")
    public String certificate() {
        return "kseta/certificate";
    }


}
