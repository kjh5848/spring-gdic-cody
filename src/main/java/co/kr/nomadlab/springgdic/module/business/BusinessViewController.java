package co.kr.nomadlab.springgdic.module.business;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/business")
public class BusinessViewController {

    // 사업개요 페이지
    @GetMapping("/outline")
    public String outline() {
        return "business/outline";
    }

    // 사업지 위치 페이지
    @GetMapping("/location")
    public String location() {
        return "business/location";
    }

    @GetMapping("/progress")
    public String progress() {
        return "business/progress";
    }

}
