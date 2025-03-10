package co.kr.nomadlab.springgdic.module.gdic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//@RequestMapping("")
public class GdicViewController {

    // 인사말 페이지
    @GetMapping("/gdic/greeting")
    public String greeting() {
        return "gdic/greeting";
    }

    @GetMapping("/kseta/about")
    public String greeting2() {
        return "kseta/about";
    }

    @GetMapping("/kseta/edu")
    public String edu() {
        return "kseta/edu";
    }

    @GetMapping("/kseta/business-goal")
    public String businessGoal() {
        return "edu";
    }

    @GetMapping("/kseta/project")
    public String project() {
        return "kseta/project";
    }

    @GetMapping("/kseta/qualification-bigdata")
    public String qualificationBigdata() {
        return "kseta/qualification-bigdata";
    }

    @GetMapping("/kseta/qualification-ai")
    public String qualificationAi() {
        return "kseta/qualification-ai";
    }

    @GetMapping("/kseta/certificate")
    public String certificate() {
        return "kseta/certificate";
    }


}
