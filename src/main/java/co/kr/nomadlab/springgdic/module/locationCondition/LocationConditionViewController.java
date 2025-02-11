package co.kr.nomadlab.springgdic.module.locationCondition;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/locationCondition")
public class LocationConditionViewController {

    // 위치 및 투자가치 페이지
    @GetMapping("/investmentValue")
    public String investmentValue() {
        return "locationCondition/investmentValue";
    }

    // 세제혜택 페이지
    @GetMapping("/taxBenefit")
    public String taxBenefit() {
        return "locationCondition/taxBenefit";
    }
}
